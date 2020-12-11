public class Account {

    private String personnummer;
    private String password;
    private String name;

    public Account(String personnummer, String password, String name) {
        super();
        this.personnummer = personnummer;
        this.password = password;
        this.name = name;
    }

    public String getPersonnummer() {
        return personnummer;
    }

    public void setPersonnummer(String personnummer) {
        this.personnummer = personnummer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}