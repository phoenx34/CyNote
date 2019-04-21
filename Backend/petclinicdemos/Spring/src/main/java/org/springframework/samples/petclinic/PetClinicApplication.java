/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.samples.petclinic.classEntity.ClEnt;
import org.springframework.samples.petclinic.lectureEntity.Lecture;
import org.springframework.samples.petclinic.lectureEntity.LectureController;
import org.springframework.samples.petclinic.storage.*;
import org.springframework.scheduling.annotation.EnableScheduling;

import net.gjerull.etherpad.client.EPLiteClient;
import org.json.simple.JSONArray;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject; 

/**
 * PetClinic Spring Boot Application.
 * 
 * @author Marc Isaac
 * @author Shen Chen
 *
 */
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.user.UserController;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableScheduling
public class PetClinicApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PetClinicApplication.class);
	}
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(PetClinicApplication.class, args);
        
    }
    
    
    
    @Bean
    CommandLineRunner init(StorageService storageService) {
    	return (args) -> {
    		storageService.deleteAll();
    		storageService.init();
    	};
    }

}
