import PHeader from "../compnents/pheader"
import BookingNotif from "../compnents/bookingNotif"
import MessageNotif from "../compnents/MessageNotif copy"
import { useEffect } from "react"
import { jwtService } from "../logic/jwt"

function PhotographerHome(){

    const tokenService = new jwtService()

    useEffect(() => {

        tokenService.checkTokenPhotographer()

    }, [])

    return(

        <div>

            <PHeader active ="Home"/>
            <div className="
            
                h-[90vh]
                flex
                flex-row
                justify-evenly
                items-center
            
            ">
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
                
                ">
                    <div className="
                    
                        w-9/10
                        flex
                        mt-5
                        
                    
                    ">
                        <p className="
                        
                            font-semibold
                            tracking-wide
                            text-neutral-700
                            text-2xl
                        
                            
                        
                        ">Bookings</p>
                    </div>
                    <div className="
                    
                        border
                        border-neutral-300
                        rounded-xl
                        w-9/10
                        h-[80%]
                        mt-5
                        shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]
                        flex
                        items-center
                        flex-col
                        pt-10
                        gap-4
                        overflow-y-auto
            
                    
                    ">
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>
                        <BookingNotif/>

                    </div>

                </div>


            </div>
        </div>

    )

}

export default PhotographerHome