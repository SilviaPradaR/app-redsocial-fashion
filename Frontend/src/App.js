import { Route, Routes } from 'react-router-dom';
import './App.css';
import Footer from './components/public/Footer';
import Main from './components/public/Main';
import Navbar from './components/public/Navbar';
import SignUp from './components/public/SignUp';

function App() {
  return (
    <div>
      
      <Routes>
        <Route exact path="/" element={<Main />}  ></Route>
        <Route exact path="/registroUser" element={<SignUp/>}  ></Route>
      </Routes>
      
    </div>
  );
}

export default App;
