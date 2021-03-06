package com.increasingly.recommender.impl.db;

import static com.increasingly.recommender.constants.Constants.*;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import com.increasingly.recommender.DB.BaseDB;
import com.increasingly.recommender.interfaces.ServiceInterface;

public class InternalProductIdList extends StoredProcedure implements ServiceInterface<ArrayList<LinkedHashMap<String, Object>>>
{
	private final static String dataSourceLookupName = "mysqlserver";
	private static final String SPROC_NAME = "Get_Internal_Product_Id_List";
	private static InternalProductIdList instance = null;
	
	private InternalProductIdList()
	{
		super(BaseDB.getJdbcTemplate(dataSourceLookupName).getDataSource(), SPROC_NAME);	
		declareParameter(new SqlParameter("FeedId", Types.INTEGER));		
		declareParameter(new SqlParameter("CustomerProductIdList", Types.VARCHAR));
		compile();		
	}

	public static InternalProductIdList getInstance()
	{
		if (instance == null)
		{
			instance = new InternalProductIdList();
		}
		return instance;
	}

	public ArrayList<LinkedHashMap<String, Object>> runService(Map<String, Object> input) 
	{		
		Integer feedId = (Integer) input.get(FEED_ID);
		String customerProductIdList = (String) input.get(PRODUCT_ID_LIST);			
		ArrayList<LinkedHashMap<String, Object>> internalProductIdList = (ArrayList<LinkedHashMap<String, Object>>) execute(feedId, customerProductIdList).get("#result-set-1");
		return internalProductIdList;
	}
}