import com.google.common.collect.Lists;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;

/**
 * Created by gao on 17-3-7.
 */
public class TestEs {

  //  select g.role_name,p.category,g.permission from permissions p ,granted_permissions g
//  where p.name=g.permission
//  @Test
  @SneakyThrows
  public void test() {
	List<String> lines = FileUtils.readLines(new File("/tmp/role.txt"));

	List<List<String>> list = Lists.newArrayList();
	lines.forEach(l -> {
	  String[] str = l.split("\t");
	  list.add(Arrays.asList(str));
	});

	list.stream().filter(l -> {
	  return l.get(0).equals("ENTERPRISE");
	}).forEach(l -> {
				 String str = "GrantedPermission " + l.get(2) + "=new GrantedPermission(\"" + l.get(0) + "\",\"" + l.get(2) + "\");";
				 String str1 = "createGrantedPermissionIfAbsent(" + l.get(2) + ");";
				 System.out.println(str);
				 System.out.println(str1);
				 System.out.println();

			   }
	);


  }

}
