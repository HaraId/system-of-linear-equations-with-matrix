package com.slem.harald.slem;

/**
 * Created by User on 1/22/2019.
 */


import java.security.InvalidParameterException;
import java.util.Arrays;

/*
    11.09.18 V0.2
    22.01.19 V1.0
 */
public class Matrix {
    private final byte m_width;
    private final byte m_height;

    private double[][] m_matrix;

    public double[][] getMatrixArray() {
        return m_matrix;
    }

    public Matrix(final int _size) {
        this(_size, _size);
    }

    public Matrix(final int _height, final int _width) {
        this.m_width = (byte) _width;
        this.m_height = (byte) _height;

        m_matrix = new double[m_height][m_width];
        for(int i = 0; i < m_height; ++i)
            m_matrix[i] = new double[m_width];
    }

    public byte width(){
        return m_width;
    }

    public byte height(){
        return m_height;
    }

    void toZero(){
        fill(0);
    }

    void fill(final int value){
        for(int i = 0; i < m_height; ++i)
            for(int j = 0; j < m_width; ++j)
                m_matrix[i][j] = value;
    }

    public boolean checkBoard(int i, int j){
        return 0 < i && i <= m_height
                && 0 < j && j <= m_width;
    }

    public void set(int i, int j, final double value){
        if(!checkBoard(i, j))
            throw new InvalidParameterException("Matrix::set element with index[" + i + ", " + j + "] - Not Found!");

        m_matrix[i - 1][j - 1] = value;
    }

    public double get(int i, int j) {
        if(!checkBoard(i, j))
            throw new InvalidParameterException("Matrix::get element with index[" + i + ", " + j + "] - Not Found!");

        return m_matrix[i - 1][j - 1];
    }

    public void setLine(int line, double ... v){
        System.arraycopy(v, 0, m_matrix[line - 1], 0, Math.min(m_width, v.length));
    }

    public void sub(double value){
        for(int i = 0; i < m_height; ++i)
            for(int j = 0; j < m_width; ++j)
                m_matrix[i][j]*= value;
    }

    public Matrix multiply(Matrix mat){
        if(width() != mat.height())
            throw new InvalidParameterException("Matrix::multiply invald matrix size for multiply");

        Matrix result = new Matrix(height(), mat.width());
        result.toZero();

        for(int i = 1; i <= result.height(); ++i)
            for(int j = 1; j <= result.width(); ++j)
                for(int k = 1; k <= width(); ++k)
                    result.set(i, j, result.get(i, j) + get(i, k) * mat.get(k, j));

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        int[] offsets = new int[m_width];
        Arrays.fill(offsets, 1);

        for(int i = 0; i < m_height; ++i) {
            for (int j = 0; j < m_width; ++j) {
                int currentOffset = Double.toString(m_matrix[i][j]).length();
                if (offsets[j] < currentOffset) {
                    offsets[j] = currentOffset;
                }
            }
        }

        for(int i = 0; i < m_height; ++i) {
            result.append("||");
            for (int j = 0; j < m_width; ++j) {
                final int currentOffset = Double.toString(m_matrix[i][j]).length();
                for(int k = 0, by = offsets[j] - currentOffset; k < by; ++k)
                    result.append(" ");
                result.append(m_matrix[i][j]);
                result.append("|");
            }
            result.append("|\n");

        }
        return result.toString();

    }

    public static double cofactor(Matrix mat, int ib, int jb){
        Matrix minor = new Matrix(mat.height() - 1);
        int ip, jp;

        final int prefix = ((ib + jb) % 2 == 0 ? 1 : -1);

        for(int i = 1; i <= minor.height(); ++i){
            for(int j = 1; j <= minor.width(); ++j){
                ip = i + ((ib <= i) ? 1 : 0);
                jp = j + ((jb <= j) ? 1 : 0);

                minor.set(i, j, mat.get(ip, jp));
            }
        }

        return  prefix * determinantNotChecked(minor, minor.height());
    }

    /*public double det(){

    }*/

    public static Matrix getInvertibleMatrix(Matrix mat){
        if(mat.width() != mat.height())
            throw new InvalidParameterException("Matrix.getInvertibleMatrix you must send only square matrix!");

        Matrix nMat = new Matrix(mat.height());

        for(int i = 1; i <= mat.height(); ++i) {
            for (int j = 1; j <= mat.width(); ++j) {
                nMat.set(j, i, Matrix.cofactor(mat, i, j));
            }
        }

        nMat.sub(1.0 / determinantNotChecked(mat, mat.width()));

        return nMat;
    }

    public static double determinant(Matrix mat){
        if(mat.width() != mat.height())
            throw new InvalidParameterException("Matrix.determinant you must send only square matrix!");

        return determinantNotChecked(mat, mat.width());
    }

    private static double determinantNotChecked(Matrix mat, final int size){
        if(mat.width() == 1)
            return  mat.get(1, 1);
        if(size == 2)
            return mat.get(1, 1) * mat.get(2, 2) - mat.get(1, 2) * mat.get(2, 1);

        double result = 0.0f;

        for(int i = 0; i < size; ++i)
            result += mat.get(1, i + 1) * cofactor(mat, 1, i + 1);

        return result;
    }
}
