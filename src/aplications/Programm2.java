package aplications;


import java.util.List;
import java.util.Scanner;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;


public class Programm2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao depDao = DaoFactory.createDepartmentrDao();
		
		System.out.println("=== Test 1: Departmrnt findById ====");
		
		Department department = depDao.findById(3);
		
		System.out.println(department);
		
		
		System.out.println("\n=== Test 2: Departmrnt findAll ====");
		
		List<Department> list = depDao.findAll();
		
		list.forEach(System.out::println);
		
		System.out.println("\n=== Test 3: Departmrnt insert====");
		Department newDep = new Department(null, "Games");
		depDao.insert(newDep);
		System.out.println("Inserted! id= " + newDep.getId());
		
		
		System.out.println("\n=== Test 4: seller update====");
		department = depDao.findById(1);
		department.setName("Informatics");
		depDao.update(department);
		System.out.println("Update completed");
		
		
		System.out.println("\n=== Test 6: seller delete===");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		depDao.deleteByID(id);
		System.out.print("Delete completed");
		
		sc.close();

	}

}
