import './App.css';
import GlobalStyle from './styles/GlobalStyle';
import Screen from './components/Screen';
import DriverAuth from './pages/DriverAuth';
import Homepage from './pages/Homepage';
import JourneyHistory from './pages/JourneyHistory';
import Loading from './pages/Loading';
import LogIn from './pages/LogIn';
import MyPage from './pages/MyPage';
import OtherUserPage from './pages/OtherUserPage';
import RatingAdd from './pages/RatingAdd';
import Rating from './pages/Rating';
import SignUp from './pages/SignUp';
import TabnidaList from './pages/TabnidaList';
import TabnidaDetail from './pages/TabnidaDetail';
import TabnidaAdd from './pages/TabnidaAdd';
import TaeoondaAdd from './pages/TaeoondaAdd';
import TabnidaEdit from './pages/TabnidaEdit';
import TaeoondaEdit from './pages/TaeoondaEdit';
import TaeoondaDetail from './pages/TaeoondaDetail';
import TaeoondaList from './pages/TaeoondaList';
import RegisterList from './pages/RegisterList';
import RegisterCheckList from './pages/RegisterCheckList';
import DestinationList from './components/Tayo/DestinationList';
import DestinationDetail from './components/Tayo/DestinationDetail';
import ExchagePage from './pages/ExchangePage';
import PointHistory from './pages/PointHistory';
import Purchase from './pages/Purchase';
import Request from './pages/Request';
import JourneyState from './pages/JourneyState';
import MyRegisterHistory from './pages/MyRegisterHistory';

import { useEffect, useState } from 'react';
import { Route, Routes } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser } from './redux/slice/UserSlice';
import { useTokenRefresh, useGetUserInfo, useIsLogin } from './hooks/useLogin';

function App() {
  const dispatch = useDispatch();
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    if (localStorage.ACCESS) {
      useGetUserInfo()
        .then(res => dispatch(loginUser(res)))
        .then(setTimeout(() => setIsLoading(false), 100));
    } else {
      setIsLoading(false);
    }
    // if (localStorage.REFRESH) {
    //   console.log(localStorage);
    //   useTokenRefresh();
    // }
  }, []);
  return (
    <>
      {isLoading || (
        <>
          <GlobalStyle />
          <Screen>
            <Routes>
              <Route path="/" element={<Homepage />}></Route>
              <Route path="/driver-auth" element={<DriverAuth />}></Route>
              <Route path="/journey-history" element={<JourneyHistory />}></Route>
              <Route path="/loading" element={<Loading />}></Route>
              <Route path="/login" element={<LogIn />}></Route>
              <Route path="/my-page" element={<MyPage />}></Route>
              <Route path="/other-user-page" element={<OtherUserPage />}></Route>
              <Route path="/rating-add" element={<RatingAdd />}></Route>
              <Route path="/rating" element={<Rating />}></Route>
              <Route path="/signup" element={<SignUp />}></Route>
              <Route path="/tabnida-add" element={<TabnidaAdd />}></Route>
              <Route path="/tabnida-edit/:yataId" element={<TabnidaEdit />}></Route>
              <Route path="/tabnida-detail/:yataId" element={<TabnidaDetail />}></Route>
              <Route path="/tabnida-list" element={<TabnidaList />}></Route>
              <Route path="/taeoonda-add" element={<TaeoondaAdd />}></Route>
              <Route path="/taeoonda-edit/:yataId" element={<TaeoondaEdit />}></Route>
              <Route path="/taeoonda-detail/:yataId" element={<TaeoondaDetail />}></Route>
              <Route path="/taeoonda-list" element={<TaeoondaList />}></Route>
              <Route path="/register-list" element={<RegisterList />}></Route>
              <Route path="/register-checklist" element={<RegisterCheckList />}></Route>
              <Route path="/destination-list" element={<DestinationList />}></Route>
              <Route path="/destination-detail" element={<DestinationDetail />}></Route>
              <Route path="/exchange-page" element={<ExchagePage />}></Route>
              <Route path="/point-history" element={<PointHistory />}></Route>
              <Route path="/purchase" element={<Purchase />}></Route>
              <Route path="/request" element={<Request />}></Route>
              <Route path="/journey-state" element={<JourneyState />}></Route>
              <Route path="/my-register-history" element={<MyRegisterHistory />}></Route>
            </Routes>
            <ToastContainer position="top-center" />
          </Screen>
        </>
      )}
    </>
  );
}

export default App;
