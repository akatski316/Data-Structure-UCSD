import java.io.*;
import java.util.*;

public class SetRangeSum {

    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;

    // Splay tree implementation

    // Vertex of a splay tree
    class Vertex {
        int key;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        long sum;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(int key, long sum, Vertex left, Vertex right, Vertex parent) {
            this.key = key;
            this.sum = sum;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    void update(Vertex v) {
        if (v == null) return;
        v.sum = v.key + (v.left != null ? v.left.sum : 0) + (v.right != null ? v.right.sum : 0);
        if (v.left != null) {
            v.left.parent = v;
        }
        if (v.right != null) {
            v.right.parent = v;
        }
    }

    void smallRotation(Vertex v) 
    {
        Vertex parent = v.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = v.parent.parent;
        if (parent.left == v) {    //..right rotation
            Vertex m = v.right;
            v.right = parent;
            parent.left = m;
        } else {                    //..left rotation
            Vertex m = v.left;
            v.left = parent;
            parent.right = m;
        }
        update(parent);
        update(v);
        v.parent = grandparent;
        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = v;
            } else {
                grandparent.right = v;
            }
        }
    }

    void bigRotation(Vertex v) {
        if (v.parent.left == v && v.parent.parent.left == v.parent) {  //..left-left
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else if (v.parent.right == v && v.parent.parent.right == v.parent) { //..right-right
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }

    // Makes splay of the given vertex and returns the new root.
    Vertex splay(Vertex v) {
        if (v == null) return null;
        while (v.parent != null) {
            if (v.parent.parent == null) {
                smallRotation(v);
                break;
            }
            bigRotation(v);
        }
        return v;
    }

    class VertexPair {
        Vertex left;
        Vertex right;
        VertexPair() {
        }
        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }
    }

    // Searches for the given key in the tree with the given root
    // and calls splay for the deepest visited node after that.
    // Returns pair of the result and the new root.
    // If found, result is a pointer to the node with the given key.
    // Otherwise, result is a pointer to the node with the smallest
    // bigger key (next value in the order).
    // If the key is bigger than all keys in the tree,
    // then result is null.
    
    VertexPair find(Vertex root, int key) {
        Vertex v = root;
        Vertex last = root;   //..last contains node we are searching for at the end of the loop if it exist
        Vertex next = null;
        while (v != null) {    //...this whole loop is like find method in notes
            if (v.key >= key && (next == null || v.key < next.key)) {
                next = v;   //..keeps track of number just greater than key or the key itself
            }
            last = v;
            if (v.key == key) {
                break;
            }
            if (v.key < key) {
                v = v.right;
            } else {
                v = v.left;
            }
        }
        root = splay(last);    //..last can be a node most closest to x or equal to x
        
        return new VertexPair(next, root);
    }

    VertexPair split(Vertex root, int key) {
        VertexPair result = new VertexPair();  //..result.left contains left subtree and so as right one
        VertexPair findAndRoot = find(root, key);
        root = findAndRoot.right;
        
        result.right = findAndRoot.left;   //..result.right contains the number just 
        if (result.right == null) {    //..if there are no right sub tree
            result.left = root;             //..left subtree starts from root
            return result;
        }

        result.right = splay(result.right);  //..take the number just greater than root to root 
       
        result.left = result.right.left;    //..now its at root and at left of root is the vertex with key
       

        result.right.left = null;   //...here spliting occurs

        if (result.left != null) {
            result.left.parent = null;  //..both gets isolated from each other
        }

        update(result.left);  //..there children get new parents
        update(result.right);
        
        return result;
    }

    Vertex merge(Vertex left, Vertex right) {  //..from yhe beginnig it is in the way such that right 
                                               //..subtree elements are always greater than left one                     
        if (left == null) return right;
        if (right == null) return left;
        
        while (right.left != null) {
            right = right.left;   //..finding smallest element in right subtree
        }
        
        right = splay(right);   
        right.left = left;
        
        update(right);
        return right;
    }

    // Code that uses splay tree to solve the problem

    Vertex root = null;

    void insert(int x) {
        Vertex left = null;
        Vertex right = null;
        Vertex new_vertex = null;

        VertexPair leftRight = split(root, x); //..the most relevant node will be made root and will be split
        
        left = leftRight.left;  //..left subtree
        right = leftRight.right;  //..right can be null only when theres no right subtree 
                            //..i.e root is the largest node
        if (right == null || right.key != x)
        {   
            new_vertex = new Vertex(x, x, null, null, null);
        }  
          

        root = merge(merge(left, new_vertex), right);
    }

    void erase(int x) {
        // Implement erase yourself
        if(!find(x)){
            return;
        }
        VertexPair trees = find(root,x);
        Vertex left = trees.right.left;
        Vertex right = trees.right;
        if(left != null)
            left.parent = null;
        right.left = null;

        right = right.right;
        
        if(right != null)
            right.parent = null;

       
        root = merge(left,right);
        
    }

    boolean find(int x) {
        Vertex v = root;
        Vertex last = root;   //..last contains node we are searching for at the end of the loop if it exist
        Vertex next = null;
        while (v != null) {    //...this whole loop is like find method in notes
            if (v.key >= x && (next == null || v.key < next.key)) {
                next = v;   //..keeps track of number just greater than key 
            }
            last = v;
            if (v.key == x) {
                break;
            }
            if (v.key < x) {
                v = v.right;
            } else {
                v = v.left;
            }
        }
        if(last == null || last.key != x )
        {
            return false;
        }
        else
            return true;
    }

    //..initially the tree is split in two halves "first half --->> root.sum < from" & 
    //..the split is done again "second half --->> root.sum > from"
    //..so middle.sum is returned

    long sum(int from, int to) {
        VertexPair leftMiddle = split(root, from);
        Vertex left = leftMiddle.left;
        Vertex middle = leftMiddle.right;
        VertexPair middleRight = split(middle, to + 1);
        middle = middleRight.left;
        Vertex right = middleRight.right;
        long ans = 0;
        if(middle != null)
            ans = middle.sum;
        update(left);
        update(middle);
        update(right);
        root = merge(merge(left, middle), right);
        return ans;
    }


    public static final int MODULO = 1000000001; 

    void solve() throws IOException {
        int n = nextInt();
        int lastone = -1;
        String str = new String();
        int last_sum_result = 0;
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            char type = nextChar();
            switch (type) {
                case '+' : {
                    int x = nextInt();
                    insert((x + last_sum_result) % MODULO);
                    flag = false;
                } break;
                case '-' : {
                    int x = nextInt();
                    erase((x + last_sum_result) % MODULO);
                    flag = false;
                } break;
                case '?' : {
                    int x = nextInt();
                    if(flag && ((x + last_sum_result) % MODULO) == lastone){
                        out.println(str);
                        flag = true;
                    }
                    else
                    {
                        out.println(find((x + last_sum_result) % MODULO) ? "Found" : "Not found");
                        lastone = ((x + last_sum_result) % MODULO);
                        str = find((x + last_sum_result) % MODULO) ? "Found" : "Not found";
                        flag = true;
                    }    
                } break;
                case 's' : {
                    int l = nextInt();
                    int r = nextInt();
                    long res = sum((l + last_sum_result) % MODULO, (r + last_sum_result) % MODULO);
                    out.println(res);
                    last_sum_result = (int)(res % MODULO);
                }
            }
        }
    }

    SetRangeSum() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new SetRangeSum();
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
    char nextChar() throws IOException {
        return nextToken().charAt(0);
    }
}
