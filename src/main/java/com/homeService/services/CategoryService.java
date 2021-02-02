package com.homeService.services;

import com.homeService.dao.CategoryDao;
import com.homeService.entity.Category;
import com.homeService.lib.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CategoryService {
    @Autowired CategoryDao categoryDao;

    public List<Category> filterIsForProduct(List<Category> categories, boolean isForProduct) {
        ArrayList<Category> resultList = new ArrayList<>();
        for (Category category : categories) {
            if (category.isForProduct() == isForProduct) resultList.add(category);
        }
        return resultList;
    }

    public Category findById(Long aLong) {
        return categoryDao.findById(aLong).get();
    }
    public Category findByName(String name) {
        return categoryDao.findByName(name);
    }

    public List<Category> findAll() {
        return categoryDao.findAll();
    }
    public List<Category> findByIdAndParent(Long aLong) {
        Deque<Category> list = new LinkedList<>();
        Long id = aLong;
        Category temp;
        do {
            temp = findById(id);
            id = temp.getParentId();
            list.addFirst(temp);
        } while (id != null && (id != 1 || !temp.getName().equals("Главная")));
        return new ArrayList(list);
    }
    /**/
    public List<Category> initPathAll(List<Category> categories) {
        for (Category c : categories) initPath(c);
        return categories;
    }
    public Category initPath(Category category) {
        ArrayList<String> path = new ArrayList<>();
        for (Category cat : findByIdAndParent(category.getId())) path.add(cat.getName());
        category.setPath(path);
        return category;
    }
    /**/
    public List<Category> getAllNotHaveParent() {
        ArrayList<Category> result = new ArrayList<>();
        List<Category> all = findAll();
        HashMap<Long, Category> mapAll = new HashMap<>();
        for (Category c : all) mapAll.put(c.getId(), c);
        for (Category c : all) {
            if (c.getParentId() == null) result.add(c);
            else mapAll.get(c.getParentId()).addDoterCategory(c);
        }
        return result;
    }
    public List<Category> findAllByParentId(Long parentId) {
        return categoryDao.findAllByParentId(parentId);
    }

    public Category save(Category category) throws Exception {
        List<Category> categories = findAllByParentId(category.getParentId());
        for (Category c : categories) if (c.getName().equals(category.getName())) throw new Exception("В этом каталоге уже существует категория с таким названием");
        return categoryDao.save(category);
    }

    public List<Category> findAllByIsForProduct(boolean isForProduct) {
        return categoryDao.findAllByIsForProduct(isForProduct);
    }
}
