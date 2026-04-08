interface Text{

    String getText();

}

class SimpleText implements Text{

    protected String val;

    SimpleText(String val){

        this.val = val;

    }

    public String getText(){

        return val;

    }

}

abstract class TextDecorator implements Text{

    Text text;

    TextDecorator(Text text){

        this.text = text;

    }

    public String getText(){

        return text.getText();

    }

}

class ItalicTextDecorator extends TextDecorator{

    ItalicTextDecorator(Text text){

        super(text);

    }

    public String getText(){

        return text.getText() + ", italic";

    }

}

class BoldTextDecorator extends TextDecorator{

    BoldTextDecorator(Text text){

        super(text);

    }

    public String getText(){

        return text.getText() + ", bold";

    }

}

class UnderlinedTextDecorator extends TextDecorator{

    UnderlinedTextDecorator(Text text){

        super(text);

    }

    public String getText(){

        return text.getText() + ", underlined";

    }

}

public class TextDecoratorDesign {

    public static void main(String[] args) {

        // 1. Simple text
        Text t0 = new SimpleText("This is a sample text");
        System.out.println("1. " + t0.getText());

        // 2. Bold only
        Text t1 = new BoldTextDecorator(
                        new SimpleText("This is a sample text"));
        System.out.println("2. " + t1.getText());

        // 3. Italic + Bold
        Text t2 = new ItalicTextDecorator(
                        new BoldTextDecorator(
                            new SimpleText("This is a sample text")));
        System.out.println("3. " + t2.getText());

        // 4. Bold + Underlined
        Text t3 = new UnderlinedTextDecorator(
                        new BoldTextDecorator(
                            new SimpleText("This is a sample text")));
        System.out.println("4. " + t3.getText());

        // 5. Bold + Italic + Underlined (all decorators)
        Text t4 = new UnderlinedTextDecorator(
                        new ItalicTextDecorator(
                            new BoldTextDecorator(
                                new SimpleText("This is a sample text"))));
        System.out.println("5. " + t4.getText());

        // 6. Different order (IMPORTANT)
        Text t5 = new BoldTextDecorator(
                        new UnderlinedTextDecorator(
                            new ItalicTextDecorator(
                                new SimpleText("This is a sample text"))));
        System.out.println("6. " + t5.getText());
    }

}