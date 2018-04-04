package com.jutem.cases.structure.trieTree;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典树节点
 */
public class TrieNode {
	private boolean isLeaf;
	private char c;
	private Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();

	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public Map<Character, TrieNode> getChildren() {
		return children;
	}
	public void setChildren(Map<Character, TrieNode> children) {
		this.children = children;
	}
	public char getC() {
		return c;
	}
	public void setC(char c) {
		this.c = c;
	}
}
