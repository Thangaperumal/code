package com.mumms;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import com.mumms.service.DataLoaderService;
import com.mumms.service.DataLoaderServiceFactory;
import com.mumms.utils.DataLoaderConstants;
import com.mumms.utils.FileUtil;

@SpringBootApplication
public class DataLoaderApplication {

	private final static Logger log = Logger.getLogger(DataLoaderApplication.class.getName());

	public static void main(String[] args) throws IOException {
		ApplicationContext ctx = SpringApplication.run(DataLoaderApplication.class, args);

		try {
			Environment env = ctx.getBean(Environment.class);
			String fileName = env.getProperty(DataLoaderConstants.FILE_NAME_PROPERTY_NAME);
			List<String> sheetNames = FileUtil.getAllSheetNames(fileName);
			sheetNames.forEach(sheetName -> {
				try {
					DataLoaderService service = DataLoaderServiceFactory.getServiceByModuleName(ctx, sheetName);
					service.loadData(fileName, sheetName);
				} catch (Exception e) {
					log.warning("An error occurred while loading data for the module " + sheetName);
					log.warning(e.getMessage());
				}
			});
		} catch (Exception e) {
			log.warning(e.getMessage());
		}

		SpringApplication.exit(ctx, new ExitCodeGenerator() {
			@Override
			public int getExitCode() {
				return 0;
			}
		});
	}
}
