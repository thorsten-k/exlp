package net.sf.exlp.xml.cdata;

public class CdataXmlEscapeHandler //implements CharacterEscapeHandler
{
//    private CharsetEncoder encoder;
// 
//    public  CdataXmlEscapeHandler(String charset)
//    {
//        encoder = Charset.forName(charset).newEncoder();
//    }
// 
//    @Override
//    public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException
//    {
//        int limit = start + length;
//        boolean cData = false;
//        StringWriter buffer = new StringWriter();
//        for (int i = start; i < limit; i++)
//        {
//            switch (ch[i])
//            {
//                case '&': buffer.write("&amp;"); break;
//                case '<': buffer.write("&lt;"); break;
//                case '>': buffer.write("&gt;"); break;
//                case '\"': if (isAttVal) {buffer.write("&quot;");} else {buffer.write('\"');} break;
//                default:
//                    if (isCDATA(ch[i]))
//                    {
//                        writeEntity(ch[i], buffer);
//                        cData = true;
//                    } else if (encoder.canEncode(ch[i])) {
//                        buffer.write(ch[i]);
//                    } else {
//                        writeEntity(ch[i], buffer);
//                    }
//            }
//        }
//        if (cData) {out.write("<![CDATA[");}
//        out.write(buffer.toString());
//        if (cData) {
//            out.write("]]>");
//        }
//    }
// 
//    private void writeEntity(char i, Writer out) throws IOException {
//        out.write("&#");
//        out.write(Integer.toString(i));
//        out.write(';');
//    }
// 
//    private boolean isCDATA(char c)
//    {
//        boolean cDataCharacter = (c < '\u0020' && c != '\t' && c != '\r' && c != '\n');
//        cDataCharacter |= (c >= '\uD800' && c < '\uE000');
//        cDataCharacter |= (c == '\uFFFE' || c == '\uFFFF');
//        return cDataCharacter;
//    }
}