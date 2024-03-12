package vn.fs.service.implement;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetBasedPageRequest implements Pageable {

    private int limit;
    private int offset;
    
    // Constructor could be expanded if sorting is needed
    public OffsetBasedPageRequest(int limit, int offset) {
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }
        this.limit = limit;
        this.offset = offset;
    }
    
	@Override
	public int getPageNumber() {
		return offset / limit;
	}

	@Override
	public int getPageSize() {
		return limit;
	}

	@Override
	public long getOffset() {
		return offset;
	}

	@Override
	public Sort getSort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pageable next() {
		return new OffsetBasedPageRequest(getPageSize(), (int) (getOffset() + getPageSize()));
	}

	@Override
	public Pageable previousOrFirst() {
		if(hasPrevious()) {
			return hasPrevious() ?
	                new OffsetBasedPageRequest(getPageSize(), (int) (getOffset() - getPageSize())): this;
		}
		
		return first();
	}

	@Override
	public Pageable first() {
		return new OffsetBasedPageRequest(getPageSize(), 0);
	}

	@Override
	public Pageable withPage(int pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return offset > limit;
	}


}
