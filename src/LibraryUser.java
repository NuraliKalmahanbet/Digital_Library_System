public class LibraryUser extends Person {
    private int userId;

    public LibraryUser(String name, int userId) {
        super(name);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String getRole() {
        return "Library User";
    }

    @Override
    public String toString() {
        return "User: " + name + " (ID: " + userId + ")";
    }
}