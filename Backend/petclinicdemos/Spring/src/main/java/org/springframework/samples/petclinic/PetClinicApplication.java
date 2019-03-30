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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import org.springframework.samples.petclinic.storage.*;

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
public class PetClinicApplication {
	
	
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
