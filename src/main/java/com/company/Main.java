package com.company;

import entity.Node;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Node head = new Node(0,null);
        Node node1 = new Node(1,null);
        Node node2 = new Node(2,null);
        head.next = node1;
        node1.next = node2;
        while (head!=null){
            System.out.println(head.val);
            head=head.next;
        }

        Map<Node,Node> map = new HashMap<>();
    }


}

class Solution {
    public Node copyRandomList(Node head) {

        return null;
    }
}