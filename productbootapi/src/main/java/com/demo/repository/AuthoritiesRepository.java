package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Authorities;

public interface AuthoritiesRepository extends JpaRepository<Authorities, String> {

}
