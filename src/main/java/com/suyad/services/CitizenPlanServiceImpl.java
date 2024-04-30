package com.suyad.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.suyad.Bindings.SearchCriteria;
import com.suyad.Emails.EmailUtils;
import com.suyad.Repo.CitizenPlanRepo;
import com.suyad.entity.CitizenPlan;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CitizenPlanServiceImpl implements CitizenPlanService 
{
	@Autowired
	private CitizenPlanRepo r;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Override
	public List<String> getPlanNames() 
	{
		return r.getPlanNames();
	}

	@Override
	public List<String> getPlanStatus() 
	{
		return r.getStatus();
	}

	@Override
	public List<CitizenPlan> searchCitizens(SearchCriteria criteria) 
	{
		CitizenPlan entity = new CitizenPlan();
		if(StringUtils.isNotBlank(criteria.getPlanName()))
		{
			entity.setPlanName(criteria.getPlanName());
		}
		if(StringUtils.isNotBlank(criteria.getPlanStatus()))
		{
			entity.setPlanStatus(criteria.getPlanStatus());
		}
		if(StringUtils.isNotBlank(criteria.getGender()))
		{
			entity.setGender(criteria.getGender());;
		}
		
		if(null!=criteria.getPlanStartDate())
		{
			entity.setPlanStartDate(criteria.getPlanStartDate());
		}
		if(null!=criteria.getPlanEndDate())
		{
			entity.setPlanStartDate(criteria.getPlanEndDate());
		}
		
		Example<CitizenPlan> of = Example.of(entity);
		return r.findAll(of);
	}

	@Override
	public void generateExcel(HttpServletResponse response) 
	{
		List<CitizenPlan> findAll = r.findAll();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet createSheet = workbook.createSheet("Data");
		HSSFRow headerRow = createSheet.createRow(0);
		
		// set data for headerRow
		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Name");
		headerRow.createCell(2).setCellValue("Gender");
		headerRow.createCell(3).setCellValue("Plan Name");
		headerRow.createCell(4).setCellValue("Plan Status");
		headerRow.createCell(5).setCellValue("SSN");
		headerRow.createCell(6).setCellValue("Phno");
		
		int rowindex = 1;
		for(CitizenPlan plan: findAll)
		{
			HSSFRow dataRow = createSheet.createRow(rowindex);
			dataRow.createCell(0).setCellValue(plan.getCitizenId());
			dataRow.createCell(1).setCellValue(plan.getName());
			dataRow.createCell(2).setCellValue(plan.getGender());
			dataRow.createCell(3).setCellValue(plan.getPlanName());
			dataRow.createCell(4).setCellValue(plan.getPlanStatus());
			dataRow.createCell(5).setCellValue(plan.getSsn());
			dataRow.createCell(6).setCellValue(plan.getPhno());
			rowindex++;
		}
		try 
		{
			//-----To send File in Email Attachment--------------
			File f = new File("data.xls");
			FileOutputStream fos = new FileOutputStream(f);
			workbook.write(fos);
			boolean sendEmail = emailUtils.sendEmail(f);
			System.out.println("Mail Has been Send with Attached File");
			//----To download File in browser---------
			ServletOutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void generatePdf(HttpServletResponse response) 
	{
		List<CitizenPlan> records = r.findAll();
		
		try 
		{
			Document PdfDoc = new Document(PageSize.A4); 
			ServletOutputStream outputStream = response.getOutputStream(); //Servlet Output Stream
			PdfWriter.getInstance(PdfDoc, outputStream);
			PdfDoc.open();
			
			Font fonttitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
			fonttitle.setSize(20);
			fonttitle.setColor(CMYKColor.BLUE);
			
			Paragraph p = new Paragraph("Citizen Plan Info",fonttitle);
			p.setAlignment(Paragraph.ALIGN_CENTER);
			PdfDoc.add(p);
			
			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100f);
			table.setWidths(new int[] {3,3,3,3,3,3,3});
			table.setSpacingBefore(5);
			
			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(CMYKColor.BLUE);
			cell.setPadding(5);
			
			Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
			font.setColor(CMYKColor.WHITE);
			
			cell.setPhrase(new Phrase("ID",font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Name",font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Gender",font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Plan Name",font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Plan Status",font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("SSN",font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Phno",font));
			table.addCell(cell);
			
			for(CitizenPlan plan:records)
			{
				table.addCell(String.valueOf(plan.getCitizenId()));
				table.addCell(plan.getName());
				table.addCell(plan.getGender());
				table.addCell(plan.getPlanName());
				table.addCell(plan.getPlanStatus());
				table.addCell(String.valueOf(plan.getSsn()));
				table.addCell(String.valueOf(plan.getPhno()));
			}
			PdfDoc.add(table);
			PdfDoc.close();
			outputStream.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
