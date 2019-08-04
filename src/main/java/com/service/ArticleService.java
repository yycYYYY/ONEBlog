package com.service;

import com.entity.Blog;
import com.entity.Category;
import com.entity.CategoryExample;
import com.entity.CommentExample;
import com.mapper.BlogMapper;
import com.mapper.CategoryMapper;
import com.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private BlogMapper blogMapper;
	@Autowired
	private CommentMapper commentMapper;

	// 获得所有的文章信息不带分类信息
	public List<Blog> getAllArticle() {
		return blogMapper.selectAll();
	}

	//通过主键获取博客文章带分类信息
	public Blog selectByPrimaryKeyWithCategory(Integer id) {
		return blogMapper.selectByPrimaryKeyWithCategory(id);
	}

	//获取所有的文章带分类信息
	public List<Blog> getAll() {
		return blogMapper.selectByExampleWithCategory(null);
	}

	// 获取所有的分类信息
	public List<Category> getCategoryName() {
		return categoryMapper.selectByExample(null);
	}

	// 得到分类名所对应的 ID 值
	public Integer getCategoryId(String categoryName) {
		CategoryExample example = new CategoryExample();
		example.createCriteria().andNameEqualTo(categoryName);
		Category category = categoryMapper.selectByExample(example).get(0);
		Integer id = category.getId();
		return id;
	}

	// 存储一篇博客
	public void save(Blog blog) {
		blogMapper.insert(blog);
	}

	// 获取 id 所对应博客文章储存在数据库的 md
	public String getArticleMdStr(int id) {
		Blog blog = blogMapper.selectByPrimaryKeyWithCategory(id);
		String md = blog.getMd();
		// md 为 null,返回空字符串
		if(md == null)
			return "";
		return md;
	}

	public Blog selectBlogById(int id) {
		Blog blog = blogMapper.selectByPrimaryKeyWithCategory(id);
		return blog;
	}

	public void deleteArticleById(int id) {
		CommentExample example = new CommentExample();
		example.createCriteria().andBlogIdEqualTo(id);
		commentMapper.deleteByExample(example);
		blogMapper.deleteByPrimaryKey(id);
	}
//这里重点关注一下，可能会出问题
	public void update(Blog blog) {
		blogMapper.updateByPrimaryKey(blog);
	}

	// postarticle
	public List<Blog> postarticle(List<Blog> list) {
		List<Blog> listRtnBlogs = new ArrayList<Blog>();
		if(list != null && list.size() > 4) {
			// 把评论数最大的四篇文章取出来
			for(int i = 0 ; i < 4; i++) {

				for(int j = i + 1; j < list.size(); j++) {
					Blog temp;
					long t1,t2;
					CommentExample example = new CommentExample();
					example.createCriteria().andBlogIdEqualTo(list.get(i).getId());
					t1 = commentMapper.countByExample(example);

					CommentExample example1 = new CommentExample();
					example1.createCriteria().andBlogIdEqualTo(list.get(j).getId());
					t2 = commentMapper.countByExample(example1);

					if(t1 < t2) {
						temp = list.get(i);
						list.set(i, list.get(j));
						list.set(j, temp);
					}
				}
			}
			listRtnBlogs = list.subList(0, 4);
		}

		if(list != null) {
			listRtnBlogs = list.subList(0, list.size());
		}
		return listRtnBlogs;
	}
}
