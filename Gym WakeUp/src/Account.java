public class Account {
    private String accountName;
    private String accountPassword;
    private String personNummer;

    public String getPersonNummer() {
        return personNummer;
    }

    public void setPersonNummer(String personNummer) {
        this.personNummer = personNummer;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountPassword() {
        return accountPassword;
    }
}
