import java.util.*;
import java.io.*;

    public class Node
    {
        public  List<Node> children = new ArrayList<Node>();
        public  Node parent = null;
        public  Integer data = null;

        public void Node(Integer data) {
            this.data = data;
        }

        public void Node(Integer data, Node parent) {
            this.data = data;
            this.parent = parent;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void setParent(Node parent) {
            //parent.addChild(this);
            this.parent = parent;
        }

        public void addChild(Integer data) {
            Node child = new Node();
            child.Node(data);
            child.setParent(this);
            this.children.add(child);

        }

        public void addChild(Node child) {
            child.setParent(this);
            this.children.add(child);
            child.hgt = this.hgt + 1;
        }

        public Integer getData() {
            return this.data;
        }

        public void setData(Integer data) {
            this.data = data;
        }

        public boolean isLeaf() {
            return this.children.size() == 0;
        }

        public static void main(String[] args)
        {
            Scanner in = new Scanner(System.in);

            int n = in.nextInt(),i,maxheight = 0,height = 0;

            Node temp = new Node();
            Node nodes[] = new Node[n]; //..creating array of nodes
            for(i = 0;i < n;i++)
                nodes[i] = new Node();  //..allocation of each node

            for(i = 0;i < n;i++)
            {
                nodes[i].Node(new Integer(in.nextInt()));
                if(nodes[i].data != -1 && nodes[i].data != i)
                    nodes[nodes[i].data].addChild(nodes[i]);
            }

            for(i = 0;i < n;i++)
            {
                if(nodes[i].isLeaf())
                {
                    height = 1;
                    temp = nodes[i];
                    while(temp.parent != null)
                    {
                        temp = temp.parent;
                        height++;
                    }
                    if(height > maxheight)
                        maxheight = height;
                }
            }

            System.out.println(maxheight);
        }
    }
