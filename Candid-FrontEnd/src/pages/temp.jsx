
import { useEffect, useState } from 'react'
import CHeader from '../compnents/cheader'
import { Link } from 'react-router-dom'

function Temp(){

    const [photographers, setPhotographers] = useState([])

    useEffect(()=>{

        fetch("/api/test/public/getPhotographers", {

            method:"POST"

        }).then(response => response.json()).then(data =>{

            setPhotographers(data.data)
            console.log(data)

        })

    }, [])


    return(

        <div className="
        
            w-screen 
            min-h-screen 
            flex 
            flex-col
            items-center
        
        ">
            <CHeader />
            <div className="
            
                flex
                border
                border-neutral-300
                w-5/10
                h-[80vh]
                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                flex-col
                items-center
                rounded-md
                
                xl:mt-8
                gap-5
                
                
            ">
                <p className='
                
                    font-semibold
                    text-2xl
                    mt-10   
                
                '>Photographer Users</p>
                {photographers? photographers.map(user => (

                    <Link to={`/makebooking/${user.userid}`} className='
                    
                        bg-black
                        w-[30vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-5

                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                        xl:w-[40vh]    
                        2xl:w-[30vh]
                    
                        justify-center
                        items-center
                        flex
                    '>
                        <p>{user.username}</p>
                    </Link>

                )): ""}
            </div>
        </div>

    )

}

export default Temp