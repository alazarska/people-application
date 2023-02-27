package com.alazarska.peopleapplication.controller;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

public class EmptyMultipartFile implements MultipartFile {

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return null;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }

        @Override
        public InputStream getInputStream() {
            return null;
        }

        @Override
        public void transferTo(File dest) throws IllegalStateException {

        }
    }
