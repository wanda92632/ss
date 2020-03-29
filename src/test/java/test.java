import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class test {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("src/main/resources/config.json"));
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = in.readLine()) != null) {
            sb.append(str);
        }
        in.close();

        String configStr = sb.toString();
        // 实例化 ObjectMapper 对象
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(configStr);
        JsonNode statesNode = rootNode.get("states");
        String states = new String(statesNode.toString().getBytes(),"UTF-8");
        HashMap<String,String> map = objectMapper.readValue(states, HashMap.class);
        System.out.println(map);
    }
}
