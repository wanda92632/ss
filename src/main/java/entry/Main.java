package entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2020/2/10 16:49
 */
public class Main {

    // 同名节点数目对应
    public static Map<String, Integer> stateCount = new HashMap<String, Integer>();
    // 国家中英文对应
    public static Map<String, String> stateMap = new HashMap<String, String>();
    // 配置路径
    public static Config config = new Config();

    // 预加载配置文件
    static {
        try {
            loadConfigFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("------------------" + "程序启动中..." + "------------------");
        System.out.println("------------------配置文件地址" + config.getConfigFilePath() + "------------------");
        System.out.println("------------------需修改配置文件地址" + config.getSourcesFilePath() + "------------------");
        // 读取源配置
        String sourceConfigFile = read(config.getSourcesFilePath());
        System.out.println("------------------源配置文件读取成功" + "------------------");
        // 读取目标配置文件
        String configFile = readFile(config.getConfigFilePath());
        System.out.println("------------------目标配置文件读取成功" + "------------------");
        // 替换配置
        String newConfigFile = configFile.replaceAll("\\[.*\\]", sourceConfigFile);
        System.out.println("------------------正在修改配置文件" + "------------------");
        // 写入目的配置文件
        writerFile(newConfigFile);
        System.out.println("------------------" + "配置文件修改成功" + "------------------");
        System.out.println("------------------" + "输入按回车键退出程序" + "------------------");
    }

    /**
     * 解析配置字符串
     *
     * @param configStr
     * @return
     */
    public static Info parse(String configStr) {
        String[] strings = configStr.split("\t");
        Info info = new Info();
        info.setServer(strings[1]);
        info.setServer_port(strings[2]);
        info.setPassword(strings[3]);
        info.setMethod(strings[4]);
        info.setPlugin("");
        info.setPlugin_opts("");
        info.setPlugin_args("");
        // 判断同一国家是否有同一节点，避免节点名一样
        if (stateCount.containsKey(strings[6])) {
            int number = stateCount.get(strings[6]);
            stateCount.put(strings[6], ++number);
        } else {
            stateCount.put(strings[6], +1);
        }
        // 判断是否有对应的中文对应节点
        if(stateMap.containsKey(strings[6])){
            info.setRemarks(stateMap.get(strings[6]) + "-" + stateCount.get(strings[6]));
            System.out.println(stateMap.get(strings[6]));
        }else {
            info.setRemarks(strings[6] + "-" + stateCount.get(strings[6]));
        }
        info.setTimeout("5");
        System.out.println("------------------" + "解析成功：" + info.getRemarks() + "------------------");
        return info;
    }

    /**
     * 读取解析源配置文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String read(String path) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        String str;
        List<Info> infos = new ArrayList<Info>();
        while ((str = in.readLine()) != null) {
            System.out.println("------------------" + "正在解析" + "------------------");
            infos.add(parse(str));
        }
        in.close();
        // 转换为 json 并返回
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(infos);
    }

    /**
     * 获取配置文件信息
     *
     * @return
     * @throws IOException
     */
    public static Config loadConfigFile() throws IOException {
        // 测试环境文件路径
//        String configFilepath = "src/main/resources/config.json";
        // 生产环境文件路径
        String configFilepath = "config.json";
        // 读取文件
        String configFile = readFile(configFilepath);
        // 转换编码
        String configStr = new String(configFile.getBytes(), "utf-8");
        // 实例化 ObjectMapper 对象
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(configStr);

        // 获取城市信息节点
        JsonNode statesNode = rootNode.get("states");
        // 转换成Map并赋值
        stateMap = objectMapper.readValue(statesNode.toString(), HashMap.class);

        // 获取文件路径节点
        JsonNode pathNode = rootNode.get("paths");
        // 单斜线替换成 双斜线
        String sourcesFilePath = pathNode.findValue("sourcesFilePath").asText().replaceAll("\\\\", "\\\\\\\\");
        String configFilePath = pathNode.findValue("configFilePath").asText().replaceAll("\\\\", "\\\\\\\\");
        // 属性
        config = new Config();
        config.setSourcesFilePath(sourcesFilePath);
        config.setConfigFilePath(configFilePath);

        return config;
    }
    /**
     * 读取文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readFile(String path) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = in.readLine()) != null) {
            sb.append(str);
        }
        in.close();
        return sb.toString();
    }

    /**
     * 写入文件
     * @param str
     */
    public static void writerFile(String str) throws IOException {
        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(config.getConfigFilePath()),"utf-8");
        BufferedWriter writer=new BufferedWriter(write);
        writer.write(str);
        writer.close();
    }

}

