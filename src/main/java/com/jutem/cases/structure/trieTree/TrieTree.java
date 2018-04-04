package com.jutem.cases.structure.trieTree;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典树
 * 只支持英文
 */
public class TrieTree {
	private TrieNode root = new TrieNode();
	
	public void insert(String word) {
		if(word == null)
			throw new RuntimeException("不允许插入空值"); 
		
		Map<Character, TrieNode> children = root.getChildren();
		
		for(int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			TrieNode node;
			
			if(children.containsKey(c)) {
				node = children.get(c);
			} else {
				node = new TrieNode();
				node.setC(c);
				node.setChildren(new HashMap<Character, TrieNode>());
				children.put(c, node);
			}

			children = node.getChildren();
			if(i == word.length() - 1)
				node.setLeaf(true);

		}
	}
	
	public TrieNode searchNode(String word) {
		if(word == null)
			return null;
		Map<Character, TrieNode> children = root.getChildren();
		
		TrieNode node = null;
		for(int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if(children.containsKey(c)) {
				node = children.get(c);
				children = node.getChildren();
			} else {
				return null;
			}
		}
		return node;
	}

}
