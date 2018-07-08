package metodos;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class ValidarString extends PlainDocument{
	@Override 
	public void insertString(int offset, String str, javax.swing.text.AttributeSet attr)
		throws BadLocationException{
			super.insertString(offset, str.replaceAll("[^a-z|^A-Z|^ ]", ""), attr);
	}
	
	public void replaceg(int offset, String str, javax.swing.text.AttributeSet attr)
			throws BadLocationException{
				super.insertString(offset, str.replaceAll("[^a-z|^A-Z|^ ]", ""), attr);
	}
		
}
	
