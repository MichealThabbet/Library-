package com.ibm.icu.text;

import com.ibm.icu.text.MessagePattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.http.message.TokenParser;

/* loaded from: classes2.dex */
public final class MessagePatternUtil {
    private MessagePatternUtil() {
    }

    public static MessageNode buildMessageNode(String str) {
        return buildMessageNode(new MessagePattern(str));
    }

    public static MessageNode buildMessageNode(MessagePattern messagePattern) {
        int iCountParts = messagePattern.countParts() - 1;
        if (iCountParts < 0) {
            throw new IllegalArgumentException("The MessagePattern is empty");
        }
        if (messagePattern.getPartType(0) != MessagePattern.Part.Type.MSG_START) {
            throw new IllegalArgumentException("The MessagePattern does not represent a MessageFormat pattern");
        }
        return buildMessageNode(messagePattern, 0, iCountParts);
    }

    public static class Node {
        /* synthetic */ Node(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Node() {
        }
    }

    public static class MessageNode extends Node {
        private volatile List<MessageContentsNode> list;

        /* synthetic */ MessageNode(AnonymousClass1 anonymousClass1) {
            this();
        }

        public List<MessageContentsNode> getContents() {
            return this.list;
        }

        public String toString() {
            return this.list.toString();
        }

        private MessageNode() {
            super(null);
            this.list = new ArrayList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addContentsNode(MessageContentsNode messageContentsNode) {
            if ((messageContentsNode instanceof TextNode) && !this.list.isEmpty()) {
                MessageContentsNode messageContentsNode2 = this.list.get(this.list.size() - 1);
                if (messageContentsNode2 instanceof TextNode) {
                    ((TextNode) messageContentsNode2).text += ((TextNode) messageContentsNode).text;
                    return;
                }
            }
            this.list.add(messageContentsNode);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MessageNode freeze() {
            this.list = Collections.unmodifiableList(this.list);
            return this;
        }
    }

    public static class MessageContentsNode extends Node {
        private Type type;

        public enum Type {
            TEXT,
            ARG,
            REPLACE_NUMBER
        }

        public String toString() {
            return "{REPLACE_NUMBER}";
        }

        /* synthetic */ MessageContentsNode(Type type, AnonymousClass1 anonymousClass1) {
            this(type);
        }

        public Type getType() {
            return this.type;
        }

        private MessageContentsNode(Type type) {
            super(null);
            this.type = type;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static MessageContentsNode createReplaceNumberNode() {
            return new MessageContentsNode(Type.REPLACE_NUMBER);
        }
    }

    public static class TextNode extends MessageContentsNode {
        private String text;

        /* synthetic */ TextNode(String str, AnonymousClass1 anonymousClass1) {
            this(str);
        }

        public String getText() {
            return this.text;
        }

        @Override // com.ibm.icu.text.MessagePatternUtil.MessageContentsNode
        public String toString() {
            return "«" + this.text + "»";
        }

        private TextNode(String str) {
            super(MessageContentsNode.Type.TEXT, null);
            this.text = str;
        }
    }

    public static class ArgNode extends MessageContentsNode {
        private MessagePattern.ArgType argType;
        private ComplexArgStyleNode complexStyle;
        private String name;
        private int number;
        private String style;
        private String typeName;

        public MessagePattern.ArgType getArgType() {
            return this.argType;
        }

        public String getName() {
            return this.name;
        }

        public int getNumber() {
            return this.number;
        }

        public String getTypeName() {
            return this.typeName;
        }

        public String getSimpleStyle() {
            return this.style;
        }

        public ComplexArgStyleNode getComplexStyle() {
            return this.complexStyle;
        }

        @Override // com.ibm.icu.text.MessagePatternUtil.MessageContentsNode
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            sb.append(this.name);
            if (this.argType != MessagePattern.ArgType.NONE) {
                sb.append(',');
                sb.append(this.typeName);
                if (this.argType == MessagePattern.ArgType.SIMPLE) {
                    if (this.style != null) {
                        sb.append(',');
                        sb.append(this.style);
                    }
                } else {
                    sb.append(',');
                    sb.append(this.complexStyle.toString());
                }
            }
            sb.append('}');
            return sb.toString();
        }

        private ArgNode() {
            super(MessageContentsNode.Type.ARG, null);
            this.number = -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static ArgNode createArgNode() {
            return new ArgNode();
        }
    }

    public static class ComplexArgStyleNode extends Node {
        private MessagePattern.ArgType argType;
        private boolean explicitOffset;
        private volatile List<VariantNode> list;
        private double offset;

        /* synthetic */ ComplexArgStyleNode(MessagePattern.ArgType argType, AnonymousClass1 anonymousClass1) {
            this(argType);
        }

        public MessagePattern.ArgType getArgType() {
            return this.argType;
        }

        public boolean hasExplicitOffset() {
            return this.explicitOffset;
        }

        public double getOffset() {
            return this.offset;
        }

        public List<VariantNode> getVariants() {
            return this.list;
        }

        public VariantNode getVariantsByType(List<VariantNode> list, List<VariantNode> list2) {
            if (list != null) {
                list.clear();
            }
            list2.clear();
            VariantNode variantNode = null;
            for (VariantNode variantNode2 : this.list) {
                if (variantNode2.isSelectorNumeric()) {
                    list.add(variantNode2);
                } else if (!"other".equals(variantNode2.getSelector())) {
                    list2.add(variantNode2);
                } else if (variantNode == null) {
                    variantNode = variantNode2;
                }
            }
            return variantNode;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('(');
            sb.append(this.argType.toString());
            sb.append(" style) ");
            if (hasExplicitOffset()) {
                sb.append("offset:");
                sb.append(this.offset);
                sb.append(TokenParser.SP);
            }
            sb.append(this.list.toString());
            return sb.toString();
        }

        private ComplexArgStyleNode(MessagePattern.ArgType argType) {
            super(null);
            this.list = new ArrayList();
            this.argType = argType;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addVariant(VariantNode variantNode) {
            this.list.add(variantNode);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public ComplexArgStyleNode freeze() {
            this.list = Collections.unmodifiableList(this.list);
            return this;
        }
    }

    public static class VariantNode extends Node {
        private MessageNode msgNode;
        private double numericValue;
        private String selector;

        /* synthetic */ VariantNode(AnonymousClass1 anonymousClass1) {
            this();
        }

        public String getSelector() {
            return this.selector;
        }

        public boolean isSelectorNumeric() {
            return this.numericValue != -1.23456789E8d;
        }

        public double getSelectorValue() {
            return this.numericValue;
        }

        public MessageNode getMessage() {
            return this.msgNode;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (isSelectorNumeric()) {
                sb.append(this.numericValue);
                sb.append(" (");
                sb.append(this.selector);
                sb.append(") {");
            } else {
                sb.append(this.selector);
                sb.append(" {");
            }
            sb.append(this.msgNode.toString());
            sb.append('}');
            return sb.toString();
        }

        private VariantNode() {
            super(null);
            this.numericValue = -1.23456789E8d;
        }
    }

    private static MessageNode buildMessageNode(MessagePattern messagePattern, int i, int i2) {
        int limit = messagePattern.getPart(i).getLimit();
        AnonymousClass1 anonymousClass1 = null;
        MessageNode messageNode = new MessageNode(anonymousClass1);
        while (true) {
            i++;
            MessagePattern.Part part = messagePattern.getPart(i);
            int index = part.getIndex();
            if (limit < index) {
                messageNode.addContentsNode(new TextNode(messagePattern.getPatternString().substring(limit, index), anonymousClass1));
            }
            if (i == i2) {
                return messageNode.freeze();
            }
            MessagePattern.Part.Type type = part.getType();
            if (type == MessagePattern.Part.Type.ARG_START) {
                int limitPartIndex = messagePattern.getLimitPartIndex(i);
                messageNode.addContentsNode(buildArgNode(messagePattern, i, limitPartIndex));
                part = messagePattern.getPart(limitPartIndex);
                i = limitPartIndex;
            } else if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                messageNode.addContentsNode(MessageContentsNode.createReplaceNumberNode());
            }
            limit = part.getLimit();
        }
    }

    private static ArgNode buildArgNode(MessagePattern messagePattern, int i, int i2) {
        ArgNode argNodeCreateArgNode = ArgNode.createArgNode();
        MessagePattern.ArgType argType = argNodeCreateArgNode.argType = messagePattern.getPart(i).getArgType();
        int i3 = i + 1;
        MessagePattern.Part part = messagePattern.getPart(i3);
        argNodeCreateArgNode.name = messagePattern.getSubstring(part);
        if (part.getType() == MessagePattern.Part.Type.ARG_NUMBER) {
            argNodeCreateArgNode.number = part.getValue();
        }
        int i4 = i3 + 1;
        int i5 = AnonymousClass1.$SwitchMap$com$ibm$icu$text$MessagePattern$ArgType[argType.ordinal()];
        if (i5 == 1) {
            int i6 = i4 + 1;
            argNodeCreateArgNode.typeName = messagePattern.getSubstring(messagePattern.getPart(i4));
            if (i6 < i2) {
                argNodeCreateArgNode.style = messagePattern.getSubstring(messagePattern.getPart(i6));
            }
        } else if (i5 == 2) {
            argNodeCreateArgNode.typeName = "choice";
            argNodeCreateArgNode.complexStyle = buildChoiceStyleNode(messagePattern, i4, i2);
        } else if (i5 == 3) {
            argNodeCreateArgNode.typeName = "plural";
            argNodeCreateArgNode.complexStyle = buildPluralStyleNode(messagePattern, i4, i2, argType);
        } else if (i5 == 4) {
            argNodeCreateArgNode.typeName = "select";
            argNodeCreateArgNode.complexStyle = buildSelectStyleNode(messagePattern, i4, i2);
        } else if (i5 == 5) {
            argNodeCreateArgNode.typeName = "selectordinal";
            argNodeCreateArgNode.complexStyle = buildPluralStyleNode(messagePattern, i4, i2, argType);
        }
        return argNodeCreateArgNode;
    }

    /* renamed from: com.ibm.icu.text.MessagePatternUtil$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ibm$icu$text$MessagePattern$ArgType;

        static {
            int[] iArr = new int[MessagePattern.ArgType.values().length];
            $SwitchMap$com$ibm$icu$text$MessagePattern$ArgType = iArr;
            try {
                iArr[MessagePattern.ArgType.SIMPLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$MessagePattern$ArgType[MessagePattern.ArgType.CHOICE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$MessagePattern$ArgType[MessagePattern.ArgType.PLURAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$MessagePattern$ArgType[MessagePattern.ArgType.SELECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ibm$icu$text$MessagePattern$ArgType[MessagePattern.ArgType.SELECTORDINAL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static ComplexArgStyleNode buildChoiceStyleNode(MessagePattern messagePattern, int i, int i2) {
        AnonymousClass1 anonymousClass1 = null;
        ComplexArgStyleNode complexArgStyleNode = new ComplexArgStyleNode(MessagePattern.ArgType.CHOICE, anonymousClass1);
        while (i < i2) {
            double numericValue = messagePattern.getNumericValue(messagePattern.getPart(i));
            int i3 = i + 2;
            int limitPartIndex = messagePattern.getLimitPartIndex(i3);
            VariantNode variantNode = new VariantNode(anonymousClass1);
            variantNode.selector = messagePattern.getSubstring(messagePattern.getPart(i + 1));
            variantNode.numericValue = numericValue;
            variantNode.msgNode = buildMessageNode(messagePattern, i3, limitPartIndex);
            complexArgStyleNode.addVariant(variantNode);
            i = limitPartIndex + 1;
        }
        return complexArgStyleNode.freeze();
    }

    private static ComplexArgStyleNode buildPluralStyleNode(MessagePattern messagePattern, int i, int i2, MessagePattern.ArgType argType) {
        AnonymousClass1 anonymousClass1 = null;
        ComplexArgStyleNode complexArgStyleNode = new ComplexArgStyleNode(argType, anonymousClass1);
        MessagePattern.Part part = messagePattern.getPart(i);
        if (part.getType().hasNumericValue()) {
            complexArgStyleNode.explicitOffset = true;
            complexArgStyleNode.offset = messagePattern.getNumericValue(part);
            i++;
        }
        while (i < i2) {
            int i3 = i + 1;
            MessagePattern.Part part2 = messagePattern.getPart(i);
            double numericValue = -1.23456789E8d;
            MessagePattern.Part part3 = messagePattern.getPart(i3);
            if (part3.getType().hasNumericValue()) {
                numericValue = messagePattern.getNumericValue(part3);
                i3++;
            }
            int limitPartIndex = messagePattern.getLimitPartIndex(i3);
            VariantNode variantNode = new VariantNode(anonymousClass1);
            variantNode.selector = messagePattern.getSubstring(part2);
            variantNode.numericValue = numericValue;
            variantNode.msgNode = buildMessageNode(messagePattern, i3, limitPartIndex);
            complexArgStyleNode.addVariant(variantNode);
            i = limitPartIndex + 1;
        }
        return complexArgStyleNode.freeze();
    }

    private static ComplexArgStyleNode buildSelectStyleNode(MessagePattern messagePattern, int i, int i2) {
        AnonymousClass1 anonymousClass1 = null;
        ComplexArgStyleNode complexArgStyleNode = new ComplexArgStyleNode(MessagePattern.ArgType.SELECT, anonymousClass1);
        while (i < i2) {
            int i3 = i + 1;
            MessagePattern.Part part = messagePattern.getPart(i);
            int limitPartIndex = messagePattern.getLimitPartIndex(i3);
            VariantNode variantNode = new VariantNode(anonymousClass1);
            variantNode.selector = messagePattern.getSubstring(part);
            variantNode.msgNode = buildMessageNode(messagePattern, i3, limitPartIndex);
            complexArgStyleNode.addVariant(variantNode);
            i = limitPartIndex + 1;
        }
        return complexArgStyleNode.freeze();
    }
}
