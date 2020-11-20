package it.nobusware.client.shader;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderUtilsShader {
    private static final String VERTEX_SHADER = "#version 130\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
    private Minecraft mc = Minecraft.getMinecraft();
    private int program = GL20.glCreateProgram();
    private long startTime = System.currentTimeMillis();
    float mousemove = 0.01f;

    public ShaderUtilsShader(String fragment) {
        this.initShader(fragment);
    }

    private void initShader(String frag) {
        int vertex = GL20.glCreateShader((int)35633);
        int fragment = GL20.glCreateShader((int)35632);
        GL20.glShaderSource((int)vertex, (CharSequence)VERTEX_SHADER);
        GL20.glShaderSource((int)fragment, (CharSequence)frag);
        GL20.glValidateProgram((int)this.program);
        GL20.glCompileShader((int)vertex);
        GL20.glCompileShader((int)fragment);
        GL20.glAttachShader((int)this.program, (int)vertex);
        GL20.glAttachShader((int)this.program, (int)fragment);
        GL20.glLinkProgram((int)this.program);
    }

    public void renderFirst() {
        GL11.glClear((int)16640);
        GL20.glUseProgram((int)this.program);
    }

    public void renderSecond() {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glBegin((int)7);
        GL11.glTexCoord2d((double)0.0, (double)1.0);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glTexCoord2d((double)0.0, (double)0.0);
        GL11.glVertex2d((double)0.0, (double)sr.getScaledHeight());
        GL11.glTexCoord2d((double)1.0, (double)0.0);
        GL11.glVertex2d((double)sr.getScaledWidth(), (double)sr.getScaledHeight());
        GL11.glTexCoord2d((double)1.0, (double)1.0);
        GL11.glVertex2d((double)sr.getScaledWidth(), (double)0.0);
        GL11.glEnd();
        GL20.glUseProgram((int)0);
    }

    public void bind() {
        GL20.glUseProgram((int)this.program);
    }

    public int getProgram() {
        return this.program;
    }

    public ShaderUtilsShader uniform1i(String loc, int i) {
        GL20.glUniform1i((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)loc), (int)i);
        return this;
    }

    public ShaderUtilsShader uniform2i(String loc, int i, int i1) {
        GL20.glUniform2i((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)loc), (int)i, (int)i1);
        return this;
    }

    public ShaderUtilsShader uniform3i(String loc, int i, int i1, int i2) {
        GL20.glUniform3i((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)loc), (int)i, (int)i1, (int)i2);
        return this;
    }

    public ShaderUtilsShader uniform4i(String loc, int i, int i1, int i2, int i3) {
        GL20.glUniform4i((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)loc), (int)i, (int)i1, (int)i2, (int)i3);
        return this;
    }

    public ShaderUtilsShader uniform1f(String loc, float f) {
        GL20.glUniform1f((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)loc), (float)f);
        return this;
    }

    public ShaderUtilsShader uniform2f(String loc, float f, float f1) {
        GL20.glUniform2f((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)loc), (float)f, (float)f1);
        return this;
    }

    public ShaderUtilsShader uniform3f(String loc, float f, float f1, float f2) {
        GL20.glUniform3f((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)loc), (float)f, (float)f1, (float)f2);
        return this;
    }

    public ShaderUtilsShader uniform4f(String loc, float f, float f1, float f2, float f3) {
        GL20.glUniform4f((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)loc), (float)f, (float)f1, (float)f2, (float)f3);
        return this;
    }

    public ShaderUtilsShader uniform1b(String loc, boolean b) {
        GL20.glUniform1i((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)loc), (int)(b ? 1 : 0));
        return this;
    }

    public void addDefaultUniforms(boolean detectmouse) {
        this.mousemove = Mouse.getX() > 957 ? (this.mousemove -= 0.002f) : (this.mousemove += 0.002f);
        float n3 = (float)Mouse.getX() / (float)this.mc.displayWidth;
        float n4 = (float)Mouse.getY() / (float)this.mc.displayHeight;
        FloatBuffer floatBuffer3 = BufferUtils.createFloatBuffer((int)2);
        floatBuffer3.position(0);
        floatBuffer3.put(n3);
        floatBuffer3.put(n4);
        floatBuffer3.flip();
        GL20.glUniform2f((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)"resolution"), (float)this.mc.displayWidth, (float)this.mc.displayHeight);
        float time = (float)(System.currentTimeMillis() - this.startTime) / 1000.0f;
        GL20.glUniform1f((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)"time"), (float)time);
        if (detectmouse) {
            GL20.glUniform2f((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)"mouse"), (float)this.mousemove, (float)0.0f);
        } else {
            GL20.glUniform2((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)"mouse"), (FloatBuffer)floatBuffer3);
        }
    }
}

