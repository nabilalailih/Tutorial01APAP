import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Nabila Laili Halimah on 11/17/2017.
 */
public class SDA1606917872L7A {

    public static void main (String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        String perintah;
        AvlTree jaringToon = new AvlTree();

        while((perintah = read.readLine()) != null) {
            String[] command = perintah.split(" ");

            if(command[0].equalsIgnoreCase("add")) {

                int popularitas = Integer.parseInt(command[2]);
                jaringToon.insert(popularitas, command[1]);
                System.out.println("Komik " + command[1] +" sudah disimpan dalam JaringToon");

            }

            else if(command[0].equalsIgnoreCase("remove")) {

                if(jaringToon.isEmpty() == true){
                    System.out.println("Tidak ada komik dalam JaringToon");
                }else {
                    int popularitas = Integer.parseInt(command[2]);
                    jaringToon.remove(popularitas, command[1]);
                    System.out.println("Komik " + command[1] + " sudah dihapus dari JaringToon");
                }

            }

            else if(command[0].equalsIgnoreCase("popularity")){

                if(jaringToon.isEmpty() == true){
                    System.out.println("Tidak ada komik dalam JaringToon");
                } else {
                    System.out.println(jaringToon.printPopularity());
                }

            }

            else if(command[0].equalsIgnoreCase("print")){

                if(jaringToon.isEmpty() == true){
                    System.out.println("Tidak ada komik dalam JaringToon");
                } else {
                    System.out.println("In Order: " + jaringToon.printInOrder().substring(0, jaringToon.printInOrder().length()-2));
                    System.out.println("Pre Order: " + jaringToon.printPreOrder().substring(0, jaringToon.printInOrder().length()-2));
                    System.out.println("Post Order: " + jaringToon.printPostOrder().substring(0, jaringToon.printInOrder().length()-2));
                }

            }
        }
    }
}

class AvlTree<T extends Comparable<? super T>> {

    private static class AvlNode<T> {
        private T           element;      // Data di dalam node
        private String 		judulKomik;
        private AvlNode<T>  left;         // Left child
        private AvlNode<T>  right;        // Right child
        private int         height;       // Height dari node
        int size = 0; //banyak node dari tree

        AvlNode( T el, String name ) {
            this( el, null, null, name );
        }

        AvlNode( T el, AvlNode<T> lt, AvlNode<T> rt, String judul ) {
            element  = el;
            left     = lt;
            right    = rt;
            height   = 0;
            judulKomik = judul;
        }
    }

    private AvlNode<T> root;

    public AvlTree( ) {
        root = null;
    }

    public void insert( T x, String name) {
        root = insert( x, root , name);
    }

    /**
     *
     * Method internal untuk menambahkan objek ke dalam tree
     * @param x elemen yang ingin ditambahkan
     * @param t posisi node saat ini
     * @return root baru dari subtree
     *
     */
    private AvlNode<T> insert( T x, AvlNode<T> t, String name ) {
        if( t == null ) {
            t = new AvlNode(x, name);
        }

        int compareResult = x.compareTo( t.element );

        //elemen ke arah kiri root
        if( compareResult < 0 ) {
            t.left = insert(x, t.left, name);
        }
        //elemen ke arah kanan root
        else if( compareResult > 0 ) {
            t.right = insert(x, t.right, name);
        }

        //balancing setelah tambah
        return balance( t, name );
    }

    /**
     * Menghapus elemen dari tree
     * @param x elemen yang akan dihapus
     */
    public void remove( T x, String title ) {
        root = remove( x, root, title );
    }

    /**
     *
     * Menghapus objek dari tree, menggunakan predecessor inorder untuk menghapus elemen yang memiliki left node dan right node
     * Manfaatkan method findMax(AvlNode<E> node) untuk mencari predecessor inorder
     * @param x elemen yang ingin dihapus
     * @param t posisi node saat ini.
     * @return true root baru dari subtree
     *
     */
    private AvlNode<T> remove( T x, AvlNode<T> t, String judul )
    {
        if( t == null ) {
            return t;
        }

        int compareResult = x.compareTo( t.element );

        //cari ke arah kiri
        if( compareResult < 0 ) {
            t.left = remove(x, t.left, judul);
        }
        //lcari ke arah kanan
        else if( compareResult > 0 ) {
            t.right = remove(x, t.right, judul);
        }
        //node memiliki 2 anak
        else if( t.left != null && t.right != null ) {
            AvlNode<T> leftMax = findMax(t.left);
            t.element = leftMax.element;
            t.judulKomik = leftMax.judulKomik;
            t.left = remove(leftMax.element, t.left, leftMax.judulKomik);
        }
        //node memiliki 1 anak
        else {
            t = ( t.left != null ) ? t.left : t.right;
        }

        return balance( t, judul );

    }

    /**
     * Mengosongkan tree
     */
    public void makeEmpty( ) {
        root = null;
    }

    /**
     *
     * Mengetahui apakah tree kosong atau tidak
     * @return true jika kosong, false jika sebaliknya
     *
     */
    public boolean isEmpty( ) {
        return root == null;
    }

    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * Mem-balance kan tree
     * Gunakan method rotateWithLeftChild, rotateWithRightChild,
     * doubleWithRightChild, dan doubleWithLeftChild
     * Jangan lupa update height dari node
     * @param //t root dari subtree yang ingin dibalance
     * @return node setelah balance
     */

    private static int max(int left, int right){
        return left > right ? left : right;
    }

    private AvlNode<T> balance( AvlNode<T> t, String judul ) {
        if( t == null ) {
            return t;
        }

        if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE ) {

            //kalau outside dari anak sebelah kiri
            //single rotation ke arah kanan
            if( height( t.left.left ) >= height( t.left.right ) ){
                System.out.println("Lakukan rotasi sekali pada "+ t.left.judulKomik);
                t = rotateWithLeftChild(t);
            }

            //kalau inside dari anak sebelah kiri
            //double rotation ke kanan lalu ke kiri
            else{
                System.out.println("Lakukan rotasi dua kali pada "+ t.left.right.judulKomik);
                t = doubleWithLeftChild(t);
            }
        }

        else {
            if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE ) {

                //kalau outside dari anak sebelah kanan
                //single rotation ke kiri
                if( height( t.right.right ) >= height( t.right.left ) ) {
                    System.out.println("Lakukan rotasi sekali pada "+ t.right.judulKomik);
                    t = rotateWithRightChild(t);
                }

                //kalau inside dari anak sebelah kanan
                //double rotation ke kiri lalu ke kanan
                else {
                    System.out.println("Lakukan rotasi dua kali pada "+ t.right.left.judulKomik);
                    t = doubleWithRightChild(t);
                }
            }
        }

        t.height = max(height(t.left), height(t.right))+1;
        return t;
    }

    /**
     * Mencari min dari sebuah subtree
     * @param t root dari sebuah subtree
     * @return node node terkecil dari subtree
     */
    private AvlNode<T> findMin( AvlNode<T> t ) {
        if( t == null ) {
            return t;
        }

        while( t.left != null ) {
            t = t.left;
        }

        return t;
    }

    /**
     * Mencari max dari sebuah subtree
     * @param t root dari sebuah subtree
     * @return node node terbesar dari subtree
     */
    private AvlNode<T> findMax( AvlNode<T> t ) {
        if( t == null ) {
            return t;
        }

        while( t.right != null ) {
            t = t.right;
        }

        return t;
    }

    public String printPopularity (){
        if(findMin(root.left) == findMax(root.right)){
            return "Hanya ada komik "+ root.judulKomik;
        } else {
            return ("Tertinggi " + findMax(root.right).judulKomik + ";" + " Terendah " +findMin(root.left).judulKomik);
        }
    }

    /**
     * Method untuk mencetak tree secara inorder
     *
     * @return String representasi dari tree
     */
    public String printInOrder() {
        StringBuilder str = new StringBuilder();
        printInOrder (root, str);
        return str.toString();
    }

    /**
     * Method internal untuk mencetak tree secara inorder
     *
     * @param t   node dari tree
     * @param str hasil dari pencetakan tree
     */
    protected void printInOrder(AvlNode<T> t, StringBuilder str) {
        if(t != null){
            printInOrder(t.left, str);
            str.append(t.judulKomik + "; ");
            printInOrder(t.right, str);
        }
    }

    /**
     * Method untuk mencetak tree secara preorder
     *
     * @return String representasi dari tree
     */
    public String printPreOrder(){
        StringBuilder str = new StringBuilder();
        printPreOrder (root, str);
        return str.toString();
    }

    /**
     * Method internal untuk mencetak tree secara preorder
     *
     * @param t   node dari tree
     * @param str hasil dari pencetakan tree
     */
    private void printPreOrder (AvlNode<T> t, StringBuilder str){
        if(t != null){
            str.append(t.judulKomik + "; ");
            printPreOrder(t.left, str);
            printPreOrder(t.right, str);
        }
    }

    /**
     * Method untuk mencetak tree secara postorder
     *
     * @return String representasi dari tree
     */
    public String printPostOrder(){
        StringBuilder str = new StringBuilder();
        printPostOrder (root, str);
        return str.toString();
    }

    /**
     * Method internal untuk mencetak tree secara postorder
     *
     * @param t   node dari tree
     * @param str hasil dari pencetakan tree
     */
    private void printPostOrder (AvlNode<T> t, StringBuilder str){
        if(t != null){
            printPostOrder(t.left, str);
            printPostOrder(t.right, str);
            str.append(t.judulKomik + "; ");
        }
    }

    /**
     * Mengembalikan height dari sebuah node
     */
    private int height( AvlNode<T> t ) {
        return t == null ? -1 : t.height;
    }

    /**
     * Merotasi subtree kiri dari sebuah node
     * Ini adalah rotasi untuk kasus 1
     * Method ini telah diimplementasikan sebagian agar anda mendapatkan gambaran rotate kasus 1
     * Jangan lupa untuk mengupdate height dari node
     * @param k2 root dari subtree
     * @return root yang telah diupdate
     */
    private AvlNode<T> rotateWithLeftChild( AvlNode<T> k2 ) {
        AvlNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;

        //update height
        k1.height = max(height(k1.left), height(k1.right))+1;
        k2.height = max(height(k2.left), height(k2.right))+1;

        return k1;
    }

    /**
     * Merotasi subtree kanan dari sebuah node
     * Ini adalah rotasi untuk kasus 4
     * Jangan lupa untuk mengupdate height dari node
     * @param //k1 root dari subtree
     * @return root yang telah diupdate
     */
    private AvlNode<T> rotateWithRightChild( AvlNode<T> k1 ) {
        AvlNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;

        k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    /**
     * Left child pertama kemudian diikuti oleh right child-nya
     * kemudian k3 dengan left child
     * Ini adalah rotasi untuk kasus 2
     * @param k3 node
     * @return root yang telah diupdate
     */
    private AvlNode<T> doubleWithLeftChild( AvlNode<T> k3 ) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     * Right child pertama kemudian diikuti oleh left child-nya
     * kemudian k1 dengan right childnya
     * Ini adalah rotasi untuk kasus 3
     * @param k1 node
     * @return root yang telah diupdate
     */
    private AvlNode<T> doubleWithRightChild( AvlNode<T> k1 ) {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }
}