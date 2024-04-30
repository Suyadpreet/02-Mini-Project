package com.suyad.Runners;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.suyad.Repo.CitizenPlanRepo;
import com.suyad.entity.CitizenPlan;

@Component
public class DataLoader implements ApplicationRunner 
{
	@Autowired
	private CitizenPlanRepo r;
	@Override
	public void run(ApplicationArguments args) throws Exception 
	{
		//r.deleteAll(); -----use if you are using mysql database
		System.out.println("Application Runner is running........");
		CitizenPlan p1 = new CitizenPlan("John", "John@gmail.com", 1234L, 95015L, "Male","Cash", "Approver", LocalDate.now(), LocalDate.now().plusMonths(6));
		CitizenPlan p2 = new CitizenPlan("Smith", "smith@gmail.com", 5678L, 99152L, "Male","Cash", "Denied", null, null);
		
		CitizenPlan p3 = new CitizenPlan("Suyadpreet", "Suyadpreet@gmail.com", 1010L, 87279L, "Male","Cash", "Approver", LocalDate.now(), LocalDate.now().plusMonths(6));
		CitizenPlan p4 = new CitizenPlan("Varunpreet", "Varunpreet@gmail.com", 5678L, 00130L, "Male","Cash", "Denied", null, null);
		
		CitizenPlan p5 = new CitizenPlan("Cathy", "Cathy@gmail.com", 9123L, 98727L,"FeMale", "Food", "Approver", LocalDate.now(), LocalDate.now().plusMonths(6));
		CitizenPlan p6 = new CitizenPlan("Johny", "Johny@gmail.com", 87446L, 94632L,"FeMale", "Food", "Denied", null, null);
		
		CitizenPlan p7 = new CitizenPlan("Sapna", "Sapna@gmail.com", 9123L, 98140L,"FeMale", "Food", "Approver", LocalDate.now(), LocalDate.now().plusMonths(6));
		CitizenPlan p8 = new CitizenPlan("Kalsi", "Kalsi@gmail.com", 87446L, 98190L,"FeMale", "Food", "Denied", null, null);
		
		CitizenPlan p9 = new CitizenPlan("Robert", "Robert@gmail.com", 9123L, 98727L,"Male", "Medical", "Approver", LocalDate.now(), LocalDate.now().plusMonths(6));
		CitizenPlan p10 = new CitizenPlan("Any", "Any@gmail.com", 87446L, 94632L,"FeMale", "Medical", "Denied", null, null);
		
		CitizenPlan p11 = new CitizenPlan("KamalPreet", "Kamalpreet@gmail.com", 9123L, 99157L,"FeMale", "Medical", "Approver", LocalDate.now(), LocalDate.now().plusMonths(6));
		CitizenPlan p12 = new CitizenPlan("Vishu", "Vishu@gmail.com", 87446L, 98151L,"FeMale", "Medical", "Denied", null, null);
		
		List<CitizenPlan> asList = Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12);
		
		r.saveAll(asList);
	}

}
