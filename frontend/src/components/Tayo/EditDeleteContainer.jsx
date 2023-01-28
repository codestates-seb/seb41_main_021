import styled from 'styled-components';
import { FiEdit, FiTrash } from 'react-icons/fi';
import { useNavigate } from 'react-router';
import useDeleteData from '../../hooks/useDeleteData';

export default function EditDeleteContainer(props) {
  const { state, yataId, data, ableTag, disableTag } = props;
  const navigate = useNavigate();

  const deleteHandler = () => {
    useDeleteData(`https://server.yata.kro.kr/api/v1/yata/${yataId}`).then(() => {
      navigate(`/${state}-list`);
    });
  };

  return (
    <Container>
      <TagContainer>
        <Tag state={data.postStatus}>{data.postStatus === 'POST_OPEN' ? ableTag : disableTag}</Tag>
      </TagContainer>
      <CRUDContainer>
        <FiEdit onClick={() => navigate(`/${state}-edit/${yataId}`)} title="수정하기" />
        <FiTrash onClick={deleteHandler} title="삭제하기" />
      </CRUDContainer>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  align-items: center;
  margin-top: 1rem;
  width: 90%;
`;

const TagContainer = styled.div``;

const CRUDContainer = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-end;

  svg {
    font-size: 1.7rem;
    margin: 0.5rem 0.8rem;
    cursor: pointer;
  }
`;

const Tag = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 0.5rem;
  padding: 0.5rem;
  width: 5rem;
  height: 1.8rem;
  color: white;
  border-radius: 0.2rem;

  background-color: ${props =>
    props.state === 'POST_OPEN' ? props.theme.colors.main_blue : props.theme.colors.light_gray};
`;
