import { Pagination as MuiPagination, PaginationItem } from '@mui/material';
import { makeStyles } from '@mui/styles';

const useStyles = makeStyles(() => ({
  ul: {
    "& .MuiPaginationItem-page.Mui-selected": {
        backgroundColor: '#C2DBE3',
        color: 'white',
    }
  }
}));

export const Pagination = props => {
  const classes = useStyles();
  const { page, totalPages, handlePageChange } = props;
 
  return(
    <>
      <MuiPagination
        page={page} // 현재페이지
        count={totalPages} //전체페이지개수
        onChange={handlePageChange} // 페이지네이션함수
        style={{ float: 'right', margin: '0 13px 16px 0' }}
        classes={{ ul: classes.ul }}
                renderItem={(item) => (
          <PaginationItem 
            {...item} 
            // className={item.page === page ? classes.selectedPage : ''}
            sx={{ fontSize: 20 }} 
          />
        )}
      />
  </>
  );
};