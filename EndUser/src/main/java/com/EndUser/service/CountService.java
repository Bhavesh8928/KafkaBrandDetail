package com.EndUser.service;


import org.springframework.stereotype.Service;

@Service
public class CountService {

    public int getCount(int num) {
        int count = 0;
        for (int i = 0; i < num; i++) {
            count++;
        }
        return count;
    }
}
