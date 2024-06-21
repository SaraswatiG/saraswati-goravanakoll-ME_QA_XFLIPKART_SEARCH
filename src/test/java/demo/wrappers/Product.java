package demo.wrappers;

class Product implements Comparable<Product> {
    String url;
    int reviewCount;
    String title;

    public Product(String title, int reviewCount, String url) {
        this.url = url;
        this.reviewCount = reviewCount;
        this.title = title;
    }

    @Override
    public int compareTo(Product product) {
        if(reviewCount==product.reviewCount)
            return 0;
        else if(product.reviewCount>reviewCount)
            return 1;
        else
            return -1;
    }
}
