package com.homeService.services;

import com.homeService.dao.CategoryDao;
import com.homeService.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CategoryService {
    @Autowired
    CategoryDao categoryDao;

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

    public ArrayList<Category> findByIdAndParent(Long aLong) {
        Deque<Category> list = new LinkedList<>();
        Long id = aLong;
        Category temp;
        do {
            temp = categoryDao.findById(id).get();
            id = temp.getParentId();
            list.addFirst(temp);
        } while (id != null && (id != 1 || !temp.getName().equals("Главная")));
        return new ArrayList(list);
    }

    public Category findByName(String name) {
        return categoryDao.findByName(name);
    }

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public Collection<Category> findAllByParentId(Long parentId) {
        return categoryDao.findAllByParentId(parentId);
    }

    public <S extends Category> S save(S s) {
        return categoryDao.save(s);
    }
}
