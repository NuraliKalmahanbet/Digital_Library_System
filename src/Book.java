public class Book {
    private String title;
    private String author;
    private int year;
    private boolean available;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.available = true;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String author;
        private int year;
        private boolean available = true;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder available(boolean available) {
            this.available = available;
            return this;
        }

        public Book build() {
            if (title == null || title.trim().isEmpty()) {
                throw new InvalidInputException("Title cannot be empty");
            }
            if (author == null || author.trim().isEmpty()) {
                throw new InvalidInputException("Author cannot be empty");
            }
            if (year < 0) {
                throw new InvalidInputException("Year cannot be negative");
            }

            Book b = new Book(title, author, year);
            b.setAvailable(available);
            return b;
        }
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + year + ") - "
                + (available ? "Available" : "Borrowed");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book book = (Book) obj;
        return title.equals(book.title) && author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return title.hashCode() + author.hashCode();
    }
}