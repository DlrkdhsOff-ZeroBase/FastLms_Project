package com.zero.fastlms.admin.model;

import lombok.Data;

@Data
public class MemberParam {

    long pageIndex;
    long pageSize;

    String searchType;
    String searchValue;


    public long getPageStart() {
        init();
        return (pageIndex - 1) * pageSize;
    }

    public long getPageEnd() {
        init();
        return pageSize;
    }

    public void init() {
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageSize < 10) {
            pageSize = 10;
        }
    }

    public String getQueryString() {
        init();
        StringBuilder sb = new StringBuilder();

        if (searchType != null && !searchValue.isEmpty()) {
            sb.append(String.format("searchType=%s", searchType));
        }

        if (searchValue != null && !searchValue.isEmpty()) {
            if (!sb.isEmpty()) {
                sb.append("&");
            }
            sb.append(String.format("searchValue=%s", searchValue));
        }
        return sb.toString();
    }
}
