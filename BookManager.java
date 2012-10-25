import java.io.IOException;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 *
 * @author �����
 * ������������������
 *
 */
public class BookManager
{

	/**
	 * @param args
	 */
	//��¼�鼮��Ϣ����
	private class BookInformation
	{
		public int BookID; //�鼮ID
		public String BookName; //�鼮����
		public int BookPage; //�鼮ҳ��
		public int TypeID; //����ID
		public String TypeName; //��������
		public String Author; //��������
		public String Translater; //��������
	}
	//˽�з���
	//�����Ļ��Ϣ
	private void ClearScreen()
	{
		for (int i = 0; i < 50; i++)
		{
			System.out.println(" ");
		}
	}
	//��ȡ�û�����
	private int GetInput()
	{
		int input = 0;
		int t = 0;
		Scanner scanner = new Scanner(System.in);
		do
		{
			ClearScreen();
			if (t > 0)
			{
				System.out.println("�����������������!");
			}
			t++;
			System.out.println("1.����һ����.................");
			System.out.println("2.��������/����ͼ��............");
			System.out.println("3.ɾ��һ����.................");
			System.out.println("4.ɾ������ͼ��................");
			System.out.println("5.����������.................");
			System.out.println("6.������/���߼���.............");
			System.out.println("7.����鼮����................");
			System.out.println("8.�˳�.....................");
			System.out.println("��ӭʹ�ã��밴��ʾ����ѡ��:");
			try
			{
				input = scanner.nextInt();
			}
			catch(Exception e)
			{
				return -1;
			}
			if (input >= 1 && input <= 9)
			{
				break;
			}
		}while(true);
		return input;
	}
	//������һ���
	private void Writeln(String info) throws IOException
	{
		System.out.println(info);
		System.in.read();
		System.in.read();
	}
	//�����������
	private void Write(String info) throws IOException
	{
		System.out.print(info);
		System.in.read();
	}
	//������
	public static void main(String[] args)throws Exception
	{
		// TODO Auto-generated method stub
		int choose = -1; //�û������ѡ��
		BookManager bookmanager = new BookManager();
		do
		{
			choose = bookmanager.GetInput();
			if (choose == -1)
			{
				System.out.println("����������������룡");
				System.in.read();
				continue;
			}
			if (choose == 8)
			{
				System.out.println("���򽫽������ټ���");
				break;
			}
			switch (choose)
			{
			case 1:
				bookmanager.InsertOne();
				break;
			case 2:
				bookmanager.InsertAll();
				break;
			case 3:
				bookmanager.DeleteOne();
				break;
			case 4:
				bookmanager.DeleteAll();
				break;
			case 5:
				bookmanager.QueryByName();
				break;
			case 6:
				bookmanager.QueryByAuthor();
				break;
			case 7:
				bookmanager.Count();
				break;
			default:
				break;
			}
		}while(choose != 8);
	}
	//ͳ�����ݿ��м�¼������
	private void Count()throws Exception
	{
		// TODO Auto-generated method stub
		String query = "select count(*) from book;";
		ResultSet rs = null;
		DataBase database = null;
		try
		{
			database = new DataBase();
			database.InitDatabase("book.db");
			rs = database.RunQuery(query);
			Writeln("���ݿ��е��鼮��¼����Ϊ:" + rs.getInt(1));
		}
		catch (Exception e)
		{
			Writeln("��ѯ�鼮����������ϸ��Ϣ��" + e.getMessage() +
					"�밴�س�������......");
			return;
		}
		finally
		{
			if (rs != null)
			{
				
			}
			database.CloseDatabase();
		}
	}
	//�����ߡ����������������ݿ�
	private void QueryByAuthor()throws Exception
	{
		// TODO Auto-generated method stub
		String writerName;
		System.out.print("������Ҫ���ҵ�����/��������:");
		Scanner scanner = new Scanner(System.in);
		writerName = scanner.next();
		String querySql = "select book.BookID,book.BookName,book.BookPage,author.Author,author.Translater,type.TypeName"
				         + " from book,author,type where (author.Author like \"%"
				         + writerName + "%\" or author.Translater like \"%" + 
				           writerName + "%\") and author.BookID = book.BookID and "
				         + "book.TypeID = type.TypeID;";
		ResultSet rs = null;
		DataBase database = null;
		try
		{
			database = new DataBase();
			database.InitDatabase("book.db");
			rs = database.RunQuery(querySql);
			int n = 0;
			System.out.println("--------------------------------------");
			while (rs.next())
			{
				System.out.println("-----------" + "��" + (n+1) + "����--------------");
				System.out.println("�鼮ID:" + rs.getInt("BookID"));
				System.out.println("�鼮����:" + rs.getString("BookName"));
				System.out.println("�鼮ҳ��:" + rs.getInt("BookPage"));
				System.out.println("����:" + rs.getString("Author"));
				System.out.println("����:" + rs.getString("Translater"));
				System.out.println("�鼮����:" + rs.getString("TypeName"));
				n++;
			}
			System.out.println("--------------------------------------");
			if (n == 0)
			{
				System.out.println("û�н����");
			}
			else
			{
				System.out.println("����" + n + "������");
			}
			Writeln("��ѯ��������ϣ����س�������......");
		}
		catch (Exception e)
		{
			Writeln("��ѯ�鼮��Ϣ������ϸ��Ϣ��" + e.getMessage() +
			           "�밴�س�������......");
			return;
		}
		finally
		{
			if (rs != null)
			{
				rs.close();
			}
			database.CloseDatabase();
		}
	}
	//��������ѯ�鼮��Ϣ
	private void QueryByName()throws Exception
	{
		// TODO Auto-generated method stub
		String bookName;
		System.out.print("������Ҫ���ҵ�����:");
		Scanner scanner = new Scanner(System.in);
		bookName = scanner.next();
		String querySql = "select book.BookID,book.BookName,book.BookPage,author.Author,author.Translater,type.TypeName"
				         + " from book,author,type where book.BookName like \"%"
				         + bookName + "%\" and author.BookID = book.BookID and "
				         + "book.TypeID = type.TypeID;";
		ResultSet rs = null;
		DataBase database = null;
		try
		{
			database = new DataBase();
			database.InitDatabase("book.db");
			rs = database.RunQuery(querySql);
			int n = 0;
			System.out.println("--------------------------------------");
			while (rs.next())
			{
				System.out.println("-----------" + "��" + (n+1) + "����--------------");
				System.out.println("�鼮ID:" + rs.getInt("BookID"));
				System.out.println("�鼮����:" + rs.getString("BookName"));
				System.out.println("�鼮ҳ��:" + rs.getInt("BookPage"));
				System.out.println("����:" + rs.getString("Author"));
				System.out.println("����:" + rs.getString("Translater"));
				System.out.println("�鼮����:" + rs.getString("TypeName"));
				n++;
			}
			System.out.println("--------------------------------------");
			if (n == 0)
			{
				System.out.println("û�н����");
			}
			else
			{
				System.out.println("����" + n + "������");
			}
			Writeln("��ѯ��������ϣ����س�������......");
		}
		catch (Exception e)
		{
			Writeln("��ѯ�鼮��Ϣ������ϸ��Ϣ��" + e.getMessage() +
			           "�밴�س�������......");
			return;
		}
		finally
		{
			if (rs != null)
			{
				rs.close();
			}
			database.CloseDatabase();
		}
	}
	//ɾ�����ݿ�������ͼ����Ϣ
	private void DeleteAll()throws Exception
	{
		// TODO Auto-generated method stub
		String ask;
		System.out.print("Ҫɾ�����ݿ����������ݣ�ȷ����?(Y/N)");
		Scanner scanner = new Scanner(System.in);
		ask = scanner.next();
		if (ask.equalsIgnoreCase("n"))
		{
			Writeln("�û�����");
			return;
		}
		if (!ask.equalsIgnoreCase("y"))
		{
			Writeln("�������");
			return;
		}
		//ɾ�����ݿ���������Ϣ
		String tableName[] = {"book", "author", "type"};
		DataBase database = null;
		try
		{
			database = new DataBase();
			database.InitDatabase("book.db");
			for (int i = 0; i < 3; i++)
			{
				String sql = "delete from " + tableName[i];
				int n = database.RunUpdate(sql);
				if (n == 0)
				{
					String info = "ɾ��" + tableName[i] + "������ʧ�ܣ����س�������......";
					System.out.println(info);
				}
				else
				{
					String info = "�ɹ�ɾ����" + tableName[i] + "���" +  n + "������,���س�������......";
					System.out.println(info);
				}
				if (i == 2)
				{
					System.in.read();
				}
			}
		}
		catch (Exception e)
		{
			Writeln("ɾ���鼮��Ϣ������ϸ��Ϣ��" + e.getMessage() +
			           "�밴�س�������......");
			return;
		}
		finally
		{
			database.CloseDatabase();
		}
	}
	
	//ɾ��һ�������Ϣ
	private void DeleteOne()throws Exception
	{
		// TODO Auto-generated method stub
		String bookName;
		System.out.println("����Ҫɾ��������:");
		Scanner scanner = new Scanner(System.in);
		bookName = scanner.next();
		String sql = "delete from book where book.BookName = \""
				     + bookName + "\";";
		DataBase database = null;
		try
		{
			database = new DataBase();
			database.InitDatabase("book.db");
			int n = database.RunUpdate(sql);
			if (n == 0)
			{
				Writeln("ɾ������ʧ�ܣ����س�������......");
			}
			else
			{
				Writeln("�ɹ�ɾ����" + n + "������,���س�������......");
			}
		}
		catch (Exception e)
		{
			Writeln("ɾ���鼮��Ϣ������ϸ��Ϣ��" + e.getMessage() +
			           "�밴�س�������......");
			return;
		}
		finally
		{
			database.CloseDatabase();
		}
	}
	//���ļ���������ͼ����Ϣ
	private void InsertAll()
	{
		// TODO Auto-generated method stub

	}
	//����һ�������Ϣ
	private void InsertOne()throws Exception
	{
		// TODO Auto-generated method stub
		ClearScreen();
		System.out.println("����һ�������Ϣ..........");
		System.out.println("�밴������˳�����룺�鼮id���������鼮ҳ�����鼮����id���鼮���͡����ߡ�" +
				"���ߣ���������\"NULL\")");
		BookInformation bookinfo = new BookInformation();
		Scanner scanner = new Scanner(System.in);
		bookinfo.BookID = scanner.nextInt();
		bookinfo.BookName = scanner.next();
		bookinfo.BookPage = scanner.nextInt();
		bookinfo.TypeID = scanner.nextInt();
		bookinfo.TypeName = scanner.next();
		bookinfo.Author = scanner.next();
		bookinfo.Translater = scanner.next();
		try
		{
			//����book��
			String sql = "insert into book values(" + bookinfo.BookID + ",\"" +
					bookinfo.BookName + "\"," + bookinfo.BookPage + "," +
					bookinfo.TypeID + ")";
			InsertDatabase(sql);
			//����type��
			sql = "insert into type values(" + bookinfo.TypeID + ",\"" + bookinfo.TypeName + "\")";
			InsertDatabase(sql);
			//����author��
			sql = "insert into author values(" + bookinfo.BookID + ",\"" + bookinfo.Translater
					+ "\",\"" + bookinfo.Author + "\")";
			int n = InsertDatabase(sql);
			if (n == 0)
			{
				Write("�������ݿ�ʧ��!���س�������......");
			}
			else
			{
				Write("������" + n + "�����ݣ����س�������......");
			}
		}
		catch (Exception e)
		{
			Writeln("�����鼮��Ϣ������ϸ��Ϣ��" + e.getMessage() +
					           "�밴�س�������......");
			return;
		}
		Write("�����鼮��Ϣ�ɹ������س�������......");
	}
	//ִ�����ݿ�������
	private int InsertDatabase(String sql)throws Exception
	{
		DataBase database = new DataBase();
		try
		{
			database.InitDatabase("book.db");
			return database.RunUpdate(sql);
		}
		finally
		{
			database.CloseDatabase();
		}
	}
}
