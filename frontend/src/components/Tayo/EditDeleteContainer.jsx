import styled from 'styled-components';
import { FiEdit, FiTrash } from 'react-icons/fi';
import { useNavigate } from 'react-router';
import useDeleteData from '../../hooks/useDeleteData';

import { useDispatch } from 'react-redux';

import { tayoDataFetch } from '../../redux/slice/DataSlice';

import { setAll } from '../../redux/slice/DestinationSlice';
export default function EditDeleteContainer(props) {
  const { state, yataId, data, ableTag, disableTag } = props;
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const deleteHandler = () => {
    useDeleteData(`https://server.yata.kro.kr/api/v1/yata/${yataId}`).then(() => {
      navigate(`/${state}-list`);
    });
  };

  const editHandler = () => {
    dispatch(tayoDataFetch(`https://server.yata.kro.kr/api/v1/yata/${yataId}`))
      .then(res => {
        console.log(res.payload.destination);
        dispatch(
          setAll(
            {
              departure: res.payload.strPoint.address,
              destination: res.payload.destination.address,
              departurePoint: {
                longitude: res.payload.strPoint.longitude,
                latitude: res.payload.strPoint.latitude,
                address: res.payload.strPoint.address,
              },
              destinationPoint: {
                longitude: res.payload.destination.longitude,
                latitude: res.payload.destination.latitude,
                address: res.payload.destination.address,
              },
              isDeparture: true,
              isDestination: true,
              departureTime: res.payload.departureTime,
              amount: res.payload.amount,
              maxPeople: res.payload.maxPeople,
              specifics: res.payload.specifics,
            },
            navigate(`/${state}-edit/${yataId}`),
          ),
        );
      })
      .then
      // setTimeout(() => {

      // }, 500),
      ();
  };

  return (
    <Container>
      <TagContainer>
        <Tag state={data.postStatus}>{data.postStatus === 'POST_OPEN' ? ableTag : disableTag}</Tag>
      </TagContainer>
      <CRUDContainer>
        <FiEdit onClick={editHandler} title="수정하기" />
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
