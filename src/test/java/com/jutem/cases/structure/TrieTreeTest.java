package com.jutem.cases.structure;

import com.jutem.cases.structure.trieTree.TrieNode;
import com.jutem.cases.structure.trieTree.TrieTree;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class TrieTreeTest {
	
	private Logger logger = LoggerFactory.getLogger(TrieTreeTest.class);
	
	@Test
	public void insert() {
		TrieTree t = new TrieTree();
		t.insert("sdfsdf");
		t.insert("sdf");
		logger.info(JSON.toJSONString(t));
	}
	
	@Test
	public void search() {
		TrieTree t = new TrieTree();
		t.insert("sdfsdf");
		t.insert("sdf");
		TrieNode node = t.searchNode("sdfsdf");
		logger.info(JSON.toJSONString(node));
		Assert.assertTrue(node.isLeaf());
	}
	
	@Test
	public void startWith() {
		TrieTree t = new TrieTree();
		t.insert("sdfsdf");
		t.insert("sdf");
		t.insert("asdf");
		
		TrieNode node = t.searchNode("sdfsd");
		Assert.assertNotNull(node);
	}
}
