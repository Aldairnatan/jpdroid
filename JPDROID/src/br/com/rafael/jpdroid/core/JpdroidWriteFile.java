package br.com.rafael.jpdroid.core;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

import android.os.Environment;
import android.util.Log;
import br.com.rafael.jpdroid.exceptions.JpdroidException;

/**
 * Classe respons�vel pela cria��o de arquivos no cart�o de mem�ria.
 * 
 * @author Rafael Centenaro
 */
public class JpdroidWriteFile {

	protected static void writeFile(String str, String fileName) throws Exception {
		if (isSDPresent()) {
			File dir = Environment.getExternalStorageDirectory();
			File file = new File(dir, fileName);

			FileWriter fw = new FileWriter(file);
			fw.write(str);
			fw.close();
		} else {
			Log.e("JpdroidException", "Nenhum cart�o de mem�ria foi localizado!");
			throw new JpdroidException("Nenhum cart�o de mem�ria foi localizado!");
		}
	}

	private static boolean isSDPresent() {
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

	}

	protected static String getDateNow() {
		Calendar c = Calendar.getInstance();
		String mYear = String.valueOf(c.get(Calendar.YEAR));
		String mMonth = String.valueOf(c.get(Calendar.MONTH));
		String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));

		return mDay + mMonth + mYear;
	}

	public JpdroidWriteFile() {
		super();
	}

}