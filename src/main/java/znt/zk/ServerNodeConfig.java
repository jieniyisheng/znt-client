package znt.zk;

/**
 * @author elviswang
 * @date 2016/12/13
 * @time 11:23
 * Desc TODO
 */
public class ServerNodeConfig {
    private String magic;
    private String host;
    private int port;
    private String name;
    private String path;

    public ServerNodeConfig(){

    }

    public ServerNodeConfig(String magic, String host, int port, String name, String path) {
        this.magic = magic;
        this.host = host;
        this.port = port;
        this.name = name;
        this.path = path;
    }

    public String buildPath(){
        return getPath() + getName() + "/" + getMagic() + ":" + getHost() + ":" + getPort();
    }

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
