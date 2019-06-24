package com.tts.BebopBlog.BlogPost;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class BlogPostController {
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	
//	to show on index.html
	@GetMapping(value = "/")
	public String index(BlogPost blogPost, Model model) {
		
		model.addAttribute("posts", blogPostRepository.findAll());
		return "blogpost/index";
	    }
	
	@GetMapping("/blogpost/new")
	public String newBlog(BlogPost blogPost) {
		return "/blogpost/new";
	}
	private BlogPost blogPost;
	
	
		@PostMapping("/blogpost/new")
	public String addNewBlogPost(BlogPost blogPost, Model model) {
		blogPostRepository.save(new BlogPost(blogPost.getId(), blogPost.getTitle(), blogPost.getAuthor(), blogPost.getBlogEntry()));
		
		model.addAttribute("id", blogPost.getId());
		model.addAttribute("title", blogPost.getTitle());
		model.addAttribute("author", blogPost.getAuthor());
		model.addAttribute("blogEntry", blogPost.getBlogEntry());
		return "blogpost/result";
	        }
	
	
		@RequestMapping(value = "/blogpost/{id}", method = RequestMethod.DELETE)
		public String deletePostWithId(@PathVariable Long id,BlogPost blogPost,Model model) {
			blogPostRepository.deleteById(id);
			
			model.addAttribute("posts",blogPostRepository.findAll());
			return "blogpost/index";

		}
		
		@RequestMapping(value="/blogpost/{id}", method = RequestMethod.GET)
		public String showPostById(@PathVariable Long id, Model model) {
			BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid id " + id));
			
			model.addAttribute("posts",blogPost);
			return "blogpost/show";
		}
		
		@RequestMapping(value="/blogpost/update/{id}", method = RequestMethod.GET)
		public String showUpdateForm(@PathVariable Long id, Model model) {
			BlogPost blogPost1 = blogPostRepository.findById(id).orElse(null);
			
			model.addAttribute("posts", blogPost1);
			return "blogpost/update";
		}
		
		@RequestMapping(value="/blogpost/{id}", method=RequestMethod.PUT)
		public String updatePostById(@PathVariable Long id, Model model, BlogPost formData) {
			BlogPost editedPost = blogPostRepository.findById(id).orElse(null);
			editedPost.setAuthor(formData.getAuthor());
			editedPost.setTitle(formData.getTitle());
			editedPost.setBlogEntry(formData.getBlogEntry());
			
			blogPostRepository.save(editedPost);
			model.addAttribute("title", editedPost.getTitle());
			model.addAttribute("author", editedPost.getAuthor());
			model.addAttribute("blogEntry", editedPost.getBlogEntry());
			model.addAttribute("id", editedPost.getId());
			return "blogpost/result";
		}
	

}
