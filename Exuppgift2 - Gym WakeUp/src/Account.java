// Denna klass Account håller information om respektive medlemskonto

public class Account {

    // Vi sparar personnummer, lösneord och namn på medlemmar.
    private String personnummer;
    private String password;
    private String name;

    public Account(String personnummer, String password, String name) {
        super();
        this.personnummer = personnummer;
        this.password = password;
        this.name = name;
    }

    // Setters och getters för respektive sparat attribut
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