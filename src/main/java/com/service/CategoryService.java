package com.service;

import com.entity.Blog;
import com.entity.BlogExample;
import com.entity.Category;
import com.mapper.BlogMapper;
import com.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
	@Autowired
	private CategoryMapper categoryMapper; 
	@Autowired
	private BlogMapper blogMapper;
	
	public List<Category> getCategory() {
		return categoryMapper.selectByExample(null);
	}

	public void newtag(String name, String level) {
		Category category = new Category();
		category.setName(name);
		if(level == null)
			level = "1";
		category.setLevel(Integer.parseInt(level));
		categoryMapper.insertSelective(category);
	}

	public void deltag(int id) {
		BlogExample example = new BlogExample();
		example.createCriteria().andCategoryidEqualTo(id);
		blogMapper.deleteByExample(example);
		categoryMapper.deleteByPrimaryKey(id);
	}

	public List<Blog> getTagArticles(int id) {
		BlogExample example = new BlogExample();
		example.createCriteria().andCategoryidEqualTo(id);
		return blogMapper.selectByExample(example);
	}
}
