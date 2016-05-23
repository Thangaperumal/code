package com.cqecom.cms.dynamicpricing;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cqecom.cms.components.commons.GlobalSettings;

public class PriceFetcher {

	private Cache cache;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public PriceFetcher(Cache cache)
	{
		this.cache=cache;
	}

	public List<PriceItem> getPrice(DynamicPriceCommand dynamicPriceCommand,GlobalSettings globalSettings) throws Exception 
	{
		List<PriceItem>  priceItems=null;
		try
		{
			String path =formPath(dynamicPriceCommand,globalSettings);
			priceItems=this.cache.get(path);

		}
		catch(ItemNotExistException itemNotExistException)
		{
			//TODO Call a Method which contacts the EC Client and form the cache

		}
		catch(Exception e)
		{
			throw e;
		}
		return priceItems;
	}

	protected String formPath(DynamicPriceCommand dynamicPriceCommand,GlobalSettings globalSettings)
	{
		String path="";
		if(PricingController.CACHETYPE.equals("NODECACHE"))
		{
			path="content/EcItems/"+globalSettings.getGlobalSite();
			String[] cacheNodeStructure=new String[]{dynamicPriceCommand.getPromo(),dynamicPriceCommand.getSplit(),dynamicPriceCommand.getEdition(),  dynamicPriceCommand.getLanguage()};
			for(int i=0;i<cacheNodeStructure.length;++i)
			{
				path+=cacheNodeStructure[i];
			}
		}
		else
		{
			path=globalSettings.getGlobalSite()+"-"+dynamicPriceCommand.getEdition()+"-"+dynamicPriceCommand.getLanguage()+"-"+dynamicPriceCommand.getSplit()+"-"+dynamicPriceCommand.getPromo();
		}
		return path;
	}



	public boolean isCacheValid(Date lastModifiedTimeStamp,Date approvedTimeStamp)
	{
		Calendar lastModified=Calendar.getInstance();
		lastModified.setTime(lastModifiedTimeStamp);
		Calendar lastApproved=Calendar.getInstance();
		lastApproved.setTime(approvedTimeStamp);
		if(lastModified.compareTo(lastApproved)>0||lastModified.compareTo(lastApproved)==0)
		{

			return true;
		}
		else
		{
			return false;
		}

	}

}
