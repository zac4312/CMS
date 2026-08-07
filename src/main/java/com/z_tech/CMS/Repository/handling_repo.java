package com.z_tech.CMS.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z_tech.CMS.Models.handling;

@Repository
public interface handling_repo extends JpaRepository<handling, UUID> {}
