package entry;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2020/2/10 16:49
 */
public class Info {
    // Vultr、电信、联通、移动 检测点
    private String server;
    // server Address
    private String server_port;
    // server port
    private String password;
    // 加密方式
    private String method;
    private String plugin;
    private String plugin_opts;
    private String plugin_args;
    // 备注
    private String remarks;
    // 超时
    private String timeout;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getServer_port() {
        return server_port;
    }

    public void setServer_port(String server_port) {
        this.server_port = server_port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

    public String getPlugin_opts() {
        return plugin_opts;
    }

    public void setPlugin_opts(String plugin_opts) {
        this.plugin_opts = plugin_opts;
    }

    public String getPlugin_args() {
        return plugin_args;
    }

    public void setPlugin_args(String plugin_args) {
        this.plugin_args = plugin_args;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
