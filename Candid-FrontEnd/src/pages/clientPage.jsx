import PHeader from "../compnents/pheader"

function ClientPage(){

    return(
        <div className="
        
            w-screen 
            min-h-screen 
        
        ">
            <PHeader active="Clients"/>
            <div className="
                
                    flex
                    border
                    border-neutral-300
                    w-8/10
                    h-[80vh]
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    flex-col
                    items-center
                    rounded-md
                    mt-10
                    justify-self-center
                    

                ">
                    <div className="
                    
                        h-2/10
                        w-9/10
                        flex
                        flex-row
                        items-center
                        justify-evenly
                        mt-5
                    
                    ">
                        <div className="
                        
                        
                        
                        ">
                            <p className="
                            
                                font-light
                                text-2xl
                            
                            ">Total Clients</p>
                            <p className="
                            
                                font-semibold
                                text-3xl
                            
                            ">5</p>
                        </div>
                        <div>
                            <p className="
                            
                                font-light
                                text-2xl
                            
                            ">Pending Requests</p>
                            <p className="
                            
                                font-semibold
                                text-3xl
                            
                            ">3</p>
                        </div>
                        <div>
                            <p className="
                            
                                font-light
                                text-2xl
                            
                            ">upcomming sessions</p>
                            <p className="
                            
                                font-semibold
                                text-3xl
                            
                            ">2</p>
                        </div>
                        <div>
                            <p className="
                            
                                font-light
                                text-2xl
                            
                            ">galleries deleivered</p>
                            <p className="
                            
                                font-semibold
                                text-3xl
                            
                            ">5</p>
                        </div>
                    </div>
                    <div className="
                    
                        outline
                        w-9/10
                        h-full
                        mb-5
                        mt-3
                    ">
                    <div>
                    <div className="
                    
                        flex
                        gap-2
                        justify-center
                        items-center
                        flex-col

                    ">
                    <div className="

                        flex 
                        gap-2
                        mb-3
                        mt-2

                    ">
                    <input placeholder="Search by name" type="text" className="
                        
                        border-2
                        border-neutral-300
                        rounded-4xl
                        pl-2
                        w-[90vh]
                        h-[4vh]

                        hover:border-neutral-400
                        active:border-neutral-500
                        hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                        focus:ring-0
                        
                    "/>
                    <button className="
                        
                        h-[4vh]
                        w-[4vh]
                        border-2
                        border-neutral-300
                        rounded-4xl
                        p-2
                        hover:border-neutral-400
                        hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                        active:border-black

                    ">
                        <img src="src/resources/Search--Streamline-Ultimate.png"/>
                    </button>
                    </div>
                    </div>              
                </div>
                </div>
            </div>
        </div>
    )

}

export default ClientPage