package pods.project.marketplaceservice.components;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import pods.project.marketplaceservice.entities.Product;
import pods.project.marketplaceservice.repositories.ProductsRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

@Component
public class ProductsPopulator {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsPopulator(ProductsRepository productsRepository) {

        this.productsRepository = productsRepository;
    }

    @PostConstruct
    public void processExcelFile() {

        ClassPathResource resource = new ClassPathResource("products.xlsx");
        
        try (InputStream inputStream = resource.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Skip header row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);

                if (row == null) continue;

                // Get cell values
                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);
                Cell descriptionCell = row.getCell(2);
                Cell priceCell = row.getCell(3);
                Cell stockQuantityCell = row.getCell(4);


                // Convert Excel model to Product entity
                Integer id = (int)(idCell.getNumericCellValue());
                Product product = productsRepository.findById(id).orElse(new Product());
                //  System.out.println(Integer.parseInt(idCell.getStringCellValue()));
                product.setId((int)(idCell.getNumericCellValue()));
                product.setName(nameCell.getStringCellValue());
                product.setDescription(descriptionCell.getStringCellValue());
                product.setPrice((int)(priceCell.getNumericCellValue()));
                product.setStock_quantity((int)(stockQuantityCell.getNumericCellValue()));

                // Save to database
                productsRepository.save(product);
            }
            System.out.printf("Products Populated Successfully");
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

