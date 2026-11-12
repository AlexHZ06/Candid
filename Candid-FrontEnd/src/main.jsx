import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Index from './pages/index'
import SignIn from './pages/signin'
import PhotographerHome from './pages/photographerHome'
import { BrowserRouter, createBrowserRouter, RouterProvider } from 'react-router-dom'
import Gallery from './pages/gallery'
import Album from './pages/album'
import AlbumEdit from './pages/albumEdit'
import ImageSearch from './pages/imageSearch'
import SignUp from './pages/signup'
import AddImage from './pages/addimage'
import AlbumCreate from './pages/albumCreate'
import CHeader from './compnents/cheader'
import RejectedJwt from './pages/rejectedJwt'
import ClientHome from './pages/ClientHome'
import Profiles from './pages/profiles'
import CreateProfile from './pages/createProflie'
import MakeBooking from './pages/makeBooking'
import EditSchedule from './pages/editSchedule'
import Temp from './pages/temp'
import ClientPage from './pages/clientPage'
import Booking from './pages/booking'
import FormEdit from './pages/formEdit'
import MakeQuestion from './pages/makeQuestion'
import FormFill from './pages/fillForm'
import BookingDetails from './pages/bookingDetails'
import CchatPage from './pages/cchatPage'
import PchatPage from './pages/pchatPage'

const router = createBrowserRouter([

  {path:"/", element: <Index/>},
  {path:"/signin", element: <SignIn/>},
  {path:"/phome", element:<PhotographerHome/>},
  {path:"/gallery", element:<Gallery/>},
  {path:"/album/:albumId/:albumName", element:<Album/>},
  {path:"/albumedit/:albumId/:albumName", element:<AlbumEdit/>},
  {path:"/imageSearch", element:<ImageSearch/>},
  {path:"/signup", element:<SignUp/>},
  {path:"/addimage/:albumId/:albumName", element:<AddImage/>},
  {path:"/rejectedjwt", element:<RejectedJwt/>},
  {path:"/albumcreate", element:<AlbumCreate/>},
  {path:"/chome", element:<ClientHome/>},
  {path:"/profiles", element:<Profiles/>},
  {path:"/createprofile", element:<CreateProfile/>},
  {path:"/makebooking/:userid", element:<MakeBooking/>},
  {path:"/editschedule", element:<EditSchedule/>},
  {path:"/temp", element:<Temp/>},
  {path:"/clientpage", element:<ClientPage/>},
  {path:"/booking/:bookingid", element:<Booking/>},
  {path:"/formEdit", element:<FormEdit/>},
  {path:"/makeQuestion", element:<MakeQuestion/>},
  {path:"/fillform/:photographerid", element:<FormFill/>},
  {path:"/bookingdetails", element:<BookingDetails/>},
  {path:"/Cchatpage", element:<CchatPage/>},
  {path:"/Pchatpage", element:<PchatPage/>}
  

])

console.log("main.jsx loaded");

const rootElement = document.getElementById('root');
console.log("Root element:", rootElement);

createRoot(rootElement).render(
  <RouterProvider router={router} />
);