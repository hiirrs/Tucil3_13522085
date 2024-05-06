import java.util.Objects;
// Kelas untuk merepresentasikan pasangan objek dengan tipe data berbeda, didefinisikan oleh parameter generic `<K, V>`. Setiap objek `Pair` memiliki `key` dan `value` untuk menyimpan informasi kunci dan nilainya
public class Pair<K, V> {
    private final K key;
    private final V value;

    // Konstruktor
    public Pair(K key, V val) {
        this.key = key;
        this.value = val;
    }

    // Akses key
    public K getKey() {
        return key; 
    }

    // Akses value
    public V getValue() {
        return value;
    }

    // Fungsi `equals` untuk memeriksa kesamaan antara dua objek `Pair`, membandingkan kunci dan nilai
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
    }
}