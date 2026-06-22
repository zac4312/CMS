package com.z_tech.CMS.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.z_tech.CMS.Models.element;

@Repository
public interface CMS_repository extends JpaRepository<element, UUID>{ }
